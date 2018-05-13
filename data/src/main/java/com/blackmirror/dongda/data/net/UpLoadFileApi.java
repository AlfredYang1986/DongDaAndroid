package com.blackmirror.dongda.data.net;


import com.blackmirror.dongda.base.AYApplication;
import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.request.UploadImageRequestBean;
import com.blackmirror.dongda.data.model.response.BaseResponseBean;
import com.blackmirror.dongda.data.model.response.OssInfoResponseBean;
import com.blackmirror.dongda.data.model.response.UpLoadImgResponseBean;
import com.blackmirror.dongda.data.utils.LogUtils;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.DateUtils;
import com.blackmirror.dongda.utils.OSSUtils;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by alfredyang on 23/05/2017.
 */
public  class UpLoadFileApi extends AYRemoteApi {


    protected static Observable<UpLoadImgResponseBean> execute(final UploadImageRequestBean requestBean, final Class<UpLoadImgResponseBean> myClass) {
        return Observable.just(requestBean)
                .map(new Function<UploadImageRequestBean, OssInfoResponseBean>() {
                    @Override
                    public OssInfoResponseBean apply(UploadImageRequestBean rBean) throws Exception {
                        if (DateUtils.isNeedRefreshToken(AYPrefUtils.getExpiration())) {
                            String json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\"}";
                            Request request = new Request.Builder()
                                    .url(DataConstant.OSS_INFO_URL).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())).build();
                            OssInfoResponseBean bean = executeRequest(request, OssInfoResponseBean.class);
                            if (bean != null && "ok".equals(bean.status) && bean.result != null && bean.result.OssConnectInfo != null) {
                                AYPrefUtils.setAccesskeyId(bean.result.OssConnectInfo.accessKeyId);
                                AYPrefUtils.setSecurityToken(bean.result.OssConnectInfo.SecurityToken);
                                AYPrefUtils.setAccesskeySecret(bean.result.OssConnectInfo.accessKeySecret);
                                AYPrefUtils.setExpiration(bean.result.OssConnectInfo.Expiration);
                            }
                            return bean;
                        } else {
                            OssInfoResponseBean bean = new OssInfoResponseBean();
                            bean.status = "ok";
                            return bean;
                        }
                    }
                }).map(new Function<OssInfoResponseBean, UpLoadImgResponseBean>() {
                    @Override
                    public UpLoadImgResponseBean apply(OssInfoResponseBean bean) throws Exception {
//                        LogUtils.d("flag", "做网络请求前的json数据: " + q.json.toString());
                        if ("ok".equals(bean.status)) {
                            return  executeUpload(requestBean);
                        } else {
                            int code = bean.error == null ? DataConstant.NET_UNKNOWN_ERROR : bean.error.code;
                            String message = bean.error == null ? "" : bean.error.message;
                            return getUploadErrorData(code,message);
                        }
                    }
                });
    }



    private static UpLoadImgResponseBean executeUpload(UploadImageRequestBean requestBean) {

        int error_code;
        String error_message;

        String path = AYApplication.getAppContext().getExternalCacheDir() + "/crop_image.jpg";

        String time = DateUtils.currentFixedSkewedTimeInRFC822Format();

//        String imgUUID = CalUtils.getUUID32();

        String imageName = requestBean.imgUUID + ".jpg";

        StringBuilder sb = new StringBuilder();
        sb.append("PUT\n")
                .append("\n")
                .append("image/jpeg\n")
                .append(time + "\n")
                .append("x-oss-security-token:" + AYPrefUtils.getSecurityToken() + "\n")
                .append("/bm-dongda/")
                .append(imageName);

        String signature = OSSUtils.sign(AYPrefUtils.getAccesskeyId(), AYPrefUtils.getAccesskeySecret(), sb.toString());

        LogUtils.d("xcx", "onClick: ziji \n" + signature);
        LogUtils.d("xcx", "onClick: ziji content \n" + sb.toString());

        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.url("https://bm-dongda.oss-cn-beijing.aliyuncs.com/" + imageName);
        requestBuilder.addHeader("Host", "bm-dongda.oss-cn-beijing.aliyuncs.com");
        requestBuilder.addHeader("Date", time);
        requestBuilder.addHeader("Content-Type", "image/jpeg");
        requestBuilder.addHeader("User-Agent", "okhttp/3.10.0");
        requestBuilder.addHeader("x-oss-security-token", AYPrefUtils.getSecurityToken());
        requestBuilder.addHeader("Authorization", signature);

        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), file);
        requestBuilder = requestBuilder.method("PUT", body);


        try {
            Response response = httpClient.newCall(requestBuilder.build()).execute();
            UpLoadImgResponseBean bean = new UpLoadImgResponseBean();
            if (response.isSuccessful()) {
                bean.status = "ok";
                bean.img_uuid = requestBean.imgUUID;

            } else {
                UpLoadImgResponseBean.ErrorBean errorBean = new UpLoadImgResponseBean.ErrorBean();
                errorBean.code = response.code();
                errorBean.message = response.message();
                bean.error = errorBean;

            }
            return bean;

        } catch (ConnectTimeoutException e1) {
            error_code = DataConstant.CONNECT_TIMEOUT_EXCEPTION;
            error_message = e1.getMessage();
            LogUtils.e(UpLoadFileApi.class, "ConnectTimeoutException: ", e1);

        } catch (SocketTimeoutException e2) {//服务器响应超时
            error_code = DataConstant.SOCKET_TIMEOUT_EXCEPTION;
            error_message = e2.getMessage();
            LogUtils.e(UpLoadFileApi.class, "SocketTimeoutException: ", e2);

        } catch (ConnectException e3) {//服务器请求超时
            error_code = DataConstant.CONNECT_EXCEPTION;
            error_message = e3.getMessage();
            LogUtils.e(UpLoadFileApi.class, "ConnectException: ", e3);

        } catch (Exception e4) {
            error_code = DataConstant.OTHER_EXCEPTION;
            error_message = e4.getMessage();
            LogUtils.e(UpLoadFileApi.class, "Exception: ", e4);

        }

        return getUploadErrorData(error_code,error_message);

    }

    private static UpLoadImgResponseBean getUploadErrorData(int error_code, String error_message){
        UpLoadImgResponseBean bean = new UpLoadImgResponseBean();
        UpLoadImgResponseBean.ErrorBean errorBean = new UpLoadImgResponseBean.ErrorBean();
        errorBean.code = error_code;
        errorBean.message = error_message;
        bean.error = errorBean;

        return bean;
    }


    private static <P extends BaseResponseBean> P getErrorData(Class clz) {
        return getErrorData(clz, DataConstant.NET_UNKNOWN_ERROR);
    }

    private static <P extends BaseResponseBean> P getErrorData(Class clz, int code) {
        return getErrorData(clz, code, "");
    }

    private static <P extends BaseResponseBean> P getErrorData(Class clz, int code, String message) {
        P obj = null;

        try {
            obj = (P) clz.newInstance();
            obj.error.code = code;
            obj.error.message = message;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

}