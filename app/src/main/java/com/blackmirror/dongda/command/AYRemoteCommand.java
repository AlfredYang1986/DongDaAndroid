package com.blackmirror.dongda.command;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.model.serverbean.ImgTokenServerBean;
import com.blackmirror.dongda.model.uibean.ImgTokenUiBean;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DateUtils;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.NetUtils;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.zip.GZIPInputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by alfredyang on 23/05/2017.
 */
public abstract class AYRemoteCommand extends AYCommand {

    private static final String imageUrl="http://192.168.100.174:9000/al/oss/gst";
    private AYSysNotificationHandler mNotificationHandler;
    private Disposable disposable;
    private Disposable muliteDisposable;

    @Override
    public <Args, Result> Result execute(Args[] args) {
        executeImpl((JSONObject[]) args);
        return null;
    }

    public void executeImpl(JSONObject... args) {
        if (NetUtils.isNetworkAvailable()) {
            getServerData(args);
        }else {//确定所有网络请求发起都在主线程
            LogUtils.d("flag","network unAvailable");
            if (mNotificationHandler==null) {
                mNotificationHandler = getTarget();
            }
            if (mNotificationHandler != null){
                mNotificationHandler.handleNotifications(getFailedCallBackName(),getErrorNetData());
            }
        }
    }

    private void getServerData(JSONObject[] args) {

        if (args == null || args.length <= 0) {
            return;
        }

        if (args.length>=2 || !DateUtils.isNeedRefreshToken(AYPrefUtils.getExpiration())){
            sendRequestData(args);
        }else {
            //先获取ImgToken再请求其他数据
            sendMuliteRequestData(args);
        }

    }

    private void sendMuliteRequestData(final JSONObject[] args) {

        Observable.just(args[0]).map(new Function<JSONObject, JSONObject>() {
            @Override
            public JSONObject apply(JSONObject object) throws Exception {
                JSONObject o = new JSONObject();
                o.put("token", AYPrefUtils.getAuthToken());
                return executeRequest(o,imageUrl);
            }
        }).map(new Function<JSONObject, JSONObject>() {
            @Override
            public JSONObject apply(JSONObject object) throws Exception {
                ImgTokenServerBean serverBean = JSON.parseObject(object.toString(), ImgTokenServerBean.class);
                ImgTokenUiBean bean = new ImgTokenUiBean(serverBean);
                if (bean.code == AppConstant.IMAGE_TOKEN_ERROR){
                    return object;
                }
                if (bean.isSuccess){
                    AYPrefUtils.setAccesskeyId(bean.accessKeyId);
                    AYPrefUtils.setSecurityToken(bean.SecurityToken);
                    AYPrefUtils.setAccesskeySecret(bean.accessKeySecret);
                    AYPrefUtils.setExpiration(bean.Expiration);
                }
                return executeRequest(args[0]);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        muliteDisposable = d;
                        if (mNotificationHandler==null) {
                            mNotificationHandler = getTarget();
                        }
                    }

                    @Override
                    public void onNext(JSONObject o) {
                        unSubscribe();
                        try {
                            if (o == null || !o.getString("status").equals("ok")) {
                                if (mNotificationHandler != null) {
                                    mNotificationHandler.handleNotifications(getFailedCallBackName(), o);
                                }
                            } else {
                                if (mNotificationHandler != null) {
                                    mNotificationHandler.handleNotifications(getSuccessCallBackName(), o);
                                }
                            }
                        } catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("flag","onError "+e.getMessage());
                        unSubscribe();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("flag","onComplete ");
                        unSubscribe();
                    }
                });
    }

    private void sendRequestData(JSONObject[] args) {
        Observable.just(args[0]).map(new Function<JSONObject, JSONObject>() {
            @Override
            public JSONObject apply(JSONObject object) throws Exception {
                return executeRequest(object);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        if (mNotificationHandler==null) {
                            mNotificationHandler = getTarget();
                        }
                    }

                    @Override
                    public void onNext(JSONObject o) {
                        unSubscribe();
                        try {
                            if (o == null || !o.getString("status").equals("ok")) {
                                if (mNotificationHandler != null) {
                                    mNotificationHandler.handleNotifications(getFailedCallBackName(), o);
                                }
                            } else {
                                if (mNotificationHandler != null) {
                                    mNotificationHandler.handleNotifications(getSuccessCallBackName(), o);
                                }
                            }
                        } catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("flag","onError "+e.getMessage());
                        unSubscribe();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("flag","onComplete ");
                        unSubscribe();
                    }
                });
    }

    private void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
        if (muliteDisposable != null && !muliteDisposable.isDisposed()) {
            muliteDisposable.dispose();
            muliteDisposable = null;
        }
    }

    private JSONObject executeRequest(JSONObject args) {
        return executeRequest(args,getUrl());
    }

    private JSONObject executeRequest(JSONObject args, String url) {
        LogUtils.d("flag","做网络请求前的json数据: "+args.toString());
        Request request = new Request.Builder()
                .url(url).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), args.toString())).build();
        try {
            Response response = httpClient.newCall(request).execute();
            InputStream in = inputStreamAfterCheck(response);
            InputStreamReader iReader = new InputStreamReader(in);
            BufferedReader bReader = new BufferedReader(iReader);
            StringBuilder json = new StringBuilder();
            String line = null;
            while ((line = bReader.readLine()) != null) {
                json.append(line).append('\n');
            }
            bReader.close();
            iReader.close();
            in.close();
            LogUtils.d("flag", "返回的数据：" + json.toString());
            JSONObject js_result = null;
            js_result = new JSONObject(json.toString());
            return js_result;
        } catch (ConnectTimeoutException e5){
            LogUtils.e(AYRemoteCommand.class,"ConnectTimeoutException: ", e5);
            return getErrorData(e5);
        } catch (SocketTimeoutException e1) {
            LogUtils.e(AYRemoteCommand.class,"SocketTimeoutException: ",e1);
            return getErrorData(e1);
        } catch (ConnectException e2) {
            LogUtils.e(AYRemoteCommand.class,"ConnectException: ",e2);
            return getErrorData(e2);
        } catch (JSONException e3) {
            LogUtils.e(AYRemoteCommand.class,"JSONException: ",e3);
            return getErrorData(e3);
        } catch (Exception e4) {
            LogUtils.e(AYRemoteCommand.class,"Exception: ",e4);
            return getErrorData(e4);
        }
    }

    private JSONObject getErrorData(Exception e) {


        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":\"error\",")
                .append("\"error\":{")
                .append("\"code\":")
                .append(AppConstant.NET_UNKNOWN_ERROR)
                .append(",\"message\":\"")
                .append(e.getMessage())
                .append("\"}}");
        JSONObject object = null;
        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e1) {

        }
        return object;
    }


    private static InputStream inputStreamAfterCheck(Response response) throws IOException {
        InputStream input = response.body().byteStream();
        if ("gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
            input = new GZIPInputStream(input);
        }
        return input;
    }

    protected abstract String getUrl();

    protected abstract String getSuccessCallBackName();

    protected abstract String getFailedCallBackName();

}
