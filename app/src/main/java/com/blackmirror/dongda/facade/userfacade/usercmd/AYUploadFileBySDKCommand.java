package com.blackmirror.dongda.facade.userfacade.usercmd;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.CalUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.NetUtils;
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.command.AYCommand;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by alfredyang on 29/06/2017.
 */
public  class AYUploadFileBySDKCommand extends AYCommand {

    final String TAG = "AYUploadFileBySDKCommand";
    private AYSysNotificationHandler notificationHandler;

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

        /*byte[] buffer = null;
        try {
             file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            LogUtils.e(AYUploadFileBySDKCommand.class,"FileNotFoundException: ",e);
        } catch (IOException e) {
            LogUtils.e(AYUploadFileBySDKCommand.class,"IOException: ",e);
        }

        if (buffer == null){
            LogUtils.d("mmp===========");
        }*/

        PutObjectRequest put = new PutObjectRequest("bm-dongda", CalUtils.md5(BasePrefUtils.getUserId())+".jpg", path);

        try {
            PutObjectResult result = OSSUtils.getOSS().putObject(put);
            LogUtils.d("flag","PutObject "+"UploadSuccess");
            LogUtils.d("flag","ETag "+result.getETag());
            LogUtils.d("flag","RequestId "+result.getRequestId());
            if (notificationHandler!=null){
                notificationHandler.handleNotifications(getSuccessCallBackName(),null);
            }
        } catch (ClientException e) {
            // 请求异常
            if (e != null) {
                // 本地异常如网络异常等
                LogUtils.e(AYUploadFileBySDKCommand.class,e);

                if (notificationHandler!=null){
                    notificationHandler.handleNotifications(getFailedCallBackName(),getErrorNetData());
                }
            }
        } catch (ServiceException e) {
            if (e != null) {
                // 服务异常
                LogUtils.e(AYUploadFileBySDKCommand.class,e.toString());
                if (notificationHandler!=null){
                    notificationHandler.handleNotifications(getFailedCallBackName(),getErrorData(e.getErrorCode(),e.getMessage()));
                }
            }
        }

        /*OSSAsyncTask task = OSSUtils.getOSS().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.d("flag","PutObject "+"UploadSuccess");
                LogUtils.d("flag","ETag "+result.getETag());
                LogUtils.d("flag","RequestId "+result.getRequestId());
                if (notificationHandler!=null){
                    notificationHandler.handleNotifications(getSuccessCallBackName(),null);
                }
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                *//*if (notificationHandler!=null){
                    notificationHandler.handleNotifications(getFailedCallBackName(),null);
                }*//*
                // 请求异常
                if (clientException != null) {
                    // 本地异常如网络异常等
                    LogUtils.e(AYUploadFileBySDKCommand.class,clientException);

                    if (notificationHandler!=null){
                        notificationHandler.handleNotifications(getFailedCallBackName(),getErrorNetData());
                    }
                }
                if (serviceException != null) {
                    // 服务异常
                    LogUtils.e(AYUploadFileBySDKCommand.class,serviceException.toString());
                    if (notificationHandler!=null){
                        notificationHandler.handleNotifications(getFailedCallBackName(),getErrorData(serviceException.getErrorCode(),serviceException.getMessage()));
                    }
                }
            }
        });*/
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


    private JSONObject getErrorData(String errorCode, String errorMessage) {


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
