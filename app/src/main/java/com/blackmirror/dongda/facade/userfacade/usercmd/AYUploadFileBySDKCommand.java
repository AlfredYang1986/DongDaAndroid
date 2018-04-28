package com.blackmirror.dongda.facade.userfacade.usercmd;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.AYPrefUtils;
import com.blackmirror.dongda.Tools.CalUtils;
import com.blackmirror.dongda.Tools.DateUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.NetUtils;
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.command.AYRemoteCommand;
import com.blackmirror.dongda.model.serverbean.ImgTokenServerBean;
import com.blackmirror.dongda.model.uibean.ImgTokenUiBean;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
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
 * Created by alfredyang on 29/06/2017.
 */
public class AYUploadFileBySDKCommand extends AYCommand {

    protected static final String imageUrl="http://192.168.100.174:9000/al/oss/gst";
    final String TAG = "AYUploadFileBySDKCommand";
    private AYSysNotificationHandler notificationHandler;
    private Disposable disposable;

    @Override
    public String getClassTag() {
        return TAG;
    }


    @Override
    public <Args, Result> Result excute(Args... args) {
        executeImpl((JSONObject[]) args);
        return null;
    }

    protected void executeImpl(JSONObject... args) {

        if (NetUtils.isNetworkAvailable()) {
            senDataToServer();
        }else {//确定所有网络请求发起都在主线程
            LogUtils.d("flag","network unAvailable");
            if (notificationHandler==null) {
                notificationHandler = getTarget();
            }
            if (notificationHandler!=null) {
                notificationHandler.handleNotifications(getFailedCallBackName(), getErrorNetData());
            }
        }


    }

    private void senDataToServer() {
        // 构造上传请求
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        File file = new File(AYApplication.getAppContext().getExternalCacheDir(), "crop_image.jpg");
        String path=AYApplication.getAppContext().getExternalCacheDir()+"/crop_image.jpg";

        if (!file.exists()){
            return;
        }

        notificationHandler=getTarget();

        Observable.just("").map(new Function<String, JSONObject>() {
            @Override
            public JSONObject apply(String s) throws Exception {
                if (OtherUtils.isNeedRefreshToken(AYPrefUtils.getExpiration())){
                    JSONObject imgToken = getImgToken();
                    ImgTokenServerBean serverBean = JSON.parseObject(imgToken.toString(), ImgTokenServerBean.class);
                    ImgTokenUiBean bean = new ImgTokenUiBean(serverBean);

                    if (bean.isSuccess){
                        AYPrefUtils.setAccesskeyId(bean.accessKeyId);
                        AYPrefUtils.setSecurityToken(bean.SecurityToken);
                        AYPrefUtils.setAccesskeySecret(bean.accessKeySecret);
                        AYPrefUtils.setExpiration(bean.Expiration);
                    }
                    return imgToken;
                }else {
                    String json="{\"status\": \"ok\"}";
                    JSONObject object = new JSONObject(json);
                    return object;
                }
            }
        }).map(new Function<JSONObject, JSONObject>() {
            @Override
            public JSONObject apply(JSONObject object) throws Exception {
                if (object == null || !object.getString("status").equals("ok")) {
                    return object;
                } else {
                    return executeUpload();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        if (notificationHandler==null) {
                            notificationHandler = getTarget();
                        }
                    }

                    @Override
                    public void onNext(JSONObject object) {
                        unDispose();
                        try {
                            if (object == null || !object.getString("status").equals("ok")) {
                                if (notificationHandler != null) {
                                    notificationHandler.handleNotifications(getFailedCallBackName(), object);
                                }
                            } else {
                                if (notificationHandler != null) {
                                    notificationHandler.handleNotifications(getSuccessCallBackName(), object);
                                }
                            }
                        } catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("flag","onError "+e.getMessage());
                        if (notificationHandler != null) {
                            notificationHandler.handleNotifications(getFailedCallBackName(), getErrorData(e));
                        }
                        unDispose();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("flag","onComplete ");
                        unDispose();
                    }
                });
    }

    private JSONObject getImgToken() throws JSONException {

        JSONObject o = new JSONObject();
        o.put("token", AYPrefUtils.getAuthToken());
        return executeRequest(o,imageUrl);
    }

    private void unDispose() {
        if (disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
            disposable=null;
        }
    }

    private JSONObject executeUpload() {
        String path=AYApplication.getAppContext().getExternalCacheDir()+"/crop_image.jpg";

        String time = DateUtils.currentFixedSkewedTimeInRFC822Format();

        String imgUUID=CalUtils.getUUID32();

        String imageName= imgUUID+".jpg";

        StringBuilder sb=new StringBuilder();
        sb.append("PUT\n")
                .append("\n")
                .append("image/jpeg\n")
                .append(time+"\n")
                .append("x-oss-security-token:"+ AYPrefUtils.getSecurityToken()+"\n")
                .append("/bm-dongda/")
                .append(imageName);

        String signature = OSSUtils.sign(AYPrefUtils.getAccesskeyId(), AYPrefUtils.getAccesskeySecret(), sb.toString());

        Log.e("xcx", "onClick: ziji \n"+signature);
        Log.e("xcx", "onClick: ziji content \n"+sb.toString());

        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.url("https://bm-dongda.oss-cn-beijing.aliyuncs.com/"+imageName);
        requestBuilder.addHeader("Host","bm-dongda.oss-cn-beijing.aliyuncs.com");
        requestBuilder.addHeader("Date",time);
        requestBuilder.addHeader("Content-Type","image/jpeg");
        requestBuilder.addHeader("User-Agent","okhttp/3.10.0");
        requestBuilder.addHeader("x-oss-security-token", AYPrefUtils.getSecurityToken());
        requestBuilder.addHeader("Authorization",signature);

        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), file);
        requestBuilder = requestBuilder.method("PUT", body);


        try {
            Response response = httpClient.newCall(requestBuilder.build()).execute();

            if (response.isSuccessful()){
                String json = "{\"status\": \"ok\",\"img_uuid\":\""+ imgUUID+"\"}";
                JSONObject object = new JSONObject(json);
                return object;
            }else {
                return getErrorData(response.code(),response.message());
            }


        } catch (ConnectTimeoutException e5){
            LogUtils.e(AYUploadFileBySDKCommand.class,"ConnectTimeoutException: ", e5);
            return getErrorData(e5);
        } catch (SocketTimeoutException e1) {
            LogUtils.e(AYUploadFileBySDKCommand.class,"SocketTimeoutException: ",e1);
            return getErrorData(e1);
        } catch (ConnectException e2) {
            LogUtils.e(AYUploadFileBySDKCommand.class,"ConnectException: ",e2);
            return getErrorData(e2);
        } catch (JSONException e3) {
            LogUtils.e(AYUploadFileBySDKCommand.class,"JSONException: ",e3);
            return getErrorData(e3);
        } catch (Exception e4) {
            LogUtils.e(AYUploadFileBySDKCommand.class,"Exception: ",e4);
            return getErrorData(e4);
        }
        
    }


    protected String getUrl() {
        //        return kDONGDABASEURL +  "v3.1/kidnap/search";
        return "http://192.168.100.174:9000/al/collections/push";
    }

    protected String getSuccessCallBackName() {
        return TAG + "Success";
    }

    protected String getFailedCallBackName() {
        return TAG + "Failed";
    }


    private JSONObject getErrorData(Throwable e) {


        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":\"error\",")
                .append("\"error\":{")
                .append("\"code\":")
                .append("-9999,")
                .append("\"message\":\"")
                .append(e.getMessage())
                .append("\"}}");
        JSONObject object = null;
        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e1) {

        }
        return object;
    }

    private JSONObject getErrorData(int errorCode, String errorMessage) {


        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":\"error\",")
                .append("\"error\":{")
                .append("\"code\":")
                .append(errorCode)
                .append(",")
                .append("\"message\":\"")
                .append(errorMessage)
                .append("\"}}");
        JSONObject object = null;
        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e1) {

        }
        return object;
    }



    protected JSONObject executeRequest(JSONObject args, String url) {
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

    private static InputStream inputStreamAfterCheck(Response response) throws IOException {
        InputStream input = response.body().byteStream();
        if ("gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
            input = new GZIPInputStream(input);
        }
        return input;
    }
}
