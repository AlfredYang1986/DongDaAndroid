package com.blackmirror.dongda.facade.userfacade.usercmd;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.LogUtils;
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

        executeImpl(null);
        return null;
    }

    public void executeImpl(JSONObject args) {
        // 构造上传请求
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        File file = new File(AYApplication.getAppConext().getExternalCacheDir(), "crop_image.jpg");

        String path=AYApplication.getAppConext().getExternalCacheDir()+"/crop_image.jpg";


        if (!file.exists()){
            return;
        }

        notificationHandler=getTarget();


        PutObjectRequest put = new PutObjectRequest("bm-dongda", BasePrefUtils.getUserId()+".jpg", path);

        OSSAsyncTask task = OSSUtils.getOSS().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
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
                if (notificationHandler!=null){
                    notificationHandler.handleNotifications(getFailedCallBackName(),null);
                }
                // 请求异常
                if (clientException != null) {
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    LogUtils.d("flag","ErrorCode "+serviceException.getErrorCode());
                    LogUtils.d("flag","RequestId "+serviceException.getRequestId());
                    LogUtils.d("flag","HostId "+serviceException.getHostId());
                    LogUtils.d("flag","RawMessage "+serviceException.getRawMessage());
                }
            }
        });
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
}
