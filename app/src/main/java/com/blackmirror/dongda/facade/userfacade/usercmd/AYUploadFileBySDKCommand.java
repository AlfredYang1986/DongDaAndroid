package com.blackmirror.dongda.facade.userfacade.usercmd;

import android.util.Log;

import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.CalUtils;
import com.blackmirror.dongda.Tools.DateUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.NetUtils;
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.command.AYCommand;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

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
public  class AYUploadFileBySDKCommand extends AYCommand {

    final String TAG = "AYUploadFileBySDKCommand";
    private AYSysNotificationHandler notificationHandler;
    private Disposable disposable;

    @Override
    public String getClassTag() {
        return TAG;
    }

    public <Args, Result> Result excute(Args ... args) {

        if (args==null || args.length<=0){
            return null;
        }

        if (NetUtils.isNetworkAvailable()) {
            executeImpl(null);
        }else {//确定所有网络请求发起都在主线程
            LogUtils.d("flag","network unAvailable");
            if (notificationHandler==null) {
                notificationHandler = getTarget();
            }
            if (notificationHandler!=null) {
                notificationHandler.handleNotifications(getFailedCallBackName(), getErrorNetData());
            }
        }

        return null;
    }

    public void executeImpl(byte[] data) {


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
                return executeUpload();
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
                        unDispose();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("flag","onComplete ");
                        unDispose();
                    }
                });


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
                .append("x-oss-security-token:"+ BasePrefUtils.getSecurityToken()+"\n")
                .append("/bm-dongda/")
                .append(imageName);

        String signature = OSSUtils.sign(BasePrefUtils.getAccesskeyId(), BasePrefUtils.getAccesskeySecret(), sb.toString());

        Log.e("xcx", "onClick: ziji \n"+signature);
        Log.e("xcx", "onClick: ziji content \n"+sb.toString());

        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.url("https://bm-dongda.oss-cn-beijing.aliyuncs.com/"+imageName);
        requestBuilder.addHeader("Host","bm-dongda.oss-cn-beijing.aliyuncs.com");
        requestBuilder.addHeader("Date",time);
        requestBuilder.addHeader("Content-Type","image/jpeg");
        requestBuilder.addHeader("User-Agent","okhttp/3.10.0");
        requestBuilder.addHeader("x-oss-security-token",BasePrefUtils.getSecurityToken());
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


    private JSONObject getErrorData(Exception e) {


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
}
