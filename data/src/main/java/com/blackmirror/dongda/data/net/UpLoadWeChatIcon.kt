package com.blackmirror.dongda.data.net

import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.request.UploadImageRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.data.model.response.DownloadWeChatIconResponseBean
import com.blackmirror.dongda.data.model.response.OssInfoResponseBean
import com.blackmirror.dongda.data.model.response.UpLoadImgResponseBean
import com.blackmirror.dongda.utils.AYPrefUtils
import com.blackmirror.dongda.utils.DateUtils
import com.blackmirror.dongda.utils.LogUtils
import com.blackmirror.dongda.utils.OSSUtils
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.apache.http.conn.ConnectTimeoutException
import java.io.File
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by xcx on 2018/6/11.
 */

fun upload2(requestBean: UploadImageRequestBean, myClass: Class<UpLoadImgResponseBean>): Observable<UpLoadImgResponseBean> {

    return Observable.just(requestBean)
            .map {
                if (DateUtils.isNeedRefreshToken(AYPrefUtils.getExpiration())) {
                    val json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\"}"
                    val request = Request.Builder()
                            .url(DataConstant.OSS_INFO_URL).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)).build()
                    val bean = executeRequest2(request, OssInfoResponseBean::class.java)

                    if (bean.status=="ok"){
                        bean.result?.OssConnectInfo?.apply {
                            AYPrefUtils.setAccesskeyId(accessKeyId)
                            AYPrefUtils.setSecurityToken(SecurityToken)
                            AYPrefUtils.setAccesskeySecret(accessKeySecret)
                            AYPrefUtils.setExpiration(Expiration)
                        }
                    }
                    bean
                } else {
                    val bean = OssInfoResponseBean()
                    bean.status = "ok"
                    bean
                }
            }.map {
                //                        LogUtils.d("flag", "做网络请求前的json数据: " + q.json.toString());
                val request = Request.Builder().url(requestBean.userIcon).get().build()
                getIcon2(request)
            }.map { bean ->
                if ("ok" == bean.status) {
                    requestBean.userIconData = bean.userIcon
                    executeUpload2(requestBean)
                } else {
                    val code = if (bean.error == null) DataConstant.NET_UNKNOWN_ERROR else bean.error!!.code
                    //                            String message = bean.error == null ? "" : bean.error.message;
                    val message = "获取微信头像失败"
                    getUploadErrorData2(code, message)
                }
            }
}


private fun getIcon2(request: Request): DownloadWeChatIconResponseBean {
    //        LogUtils.d("flag","做网络请求前的json数据: "+args.toString());
    val error_code: Int
    val error_message: String?
    val bean = DownloadWeChatIconResponseBean()
    try {
        val response = BaseApi.httpClient.newCall(request).execute()
        if (response.isSuccessful) {
            bean.status = "ok"
            bean.userIcon = response.body()!!.bytes()
        } else {
            bean.error = BaseResponseBean.ErrorBean()
            bean.error!!.code = response.code()
            bean.error!!.message = response.message()
        }
        response.close()
        return bean

    } catch (e1: ConnectTimeoutException) {
        error_code = DataConstant.CONNECT_TIMEOUT_EXCEPTION
        error_message = e1.message
        LogUtils.e(AYRemoteApi::class.java, "ConnectTimeoutException: ", e1)

    } catch (e2: SocketTimeoutException) {//服务器响应超时
        error_code = DataConstant.SOCKET_TIMEOUT_EXCEPTION
        error_message = e2.message
        LogUtils.e(AYRemoteApi::class.java, "SocketTimeoutException: ", e2)

    } catch (e3: ConnectException) {//服务器请求超时
        error_code = DataConstant.CONNECT_EXCEPTION
        error_message = e3.message
        LogUtils.e(AYRemoteApi::class.java, "ConnectException: ", e3)

    } catch (e4: Exception) {
        error_code = DataConstant.OTHER_EXCEPTION
        error_message = e4.message
        LogUtils.e(AYRemoteApi::class.java, "Exception: ", e4)

    }

    bean.error = BaseResponseBean.ErrorBean()
    bean.error!!.code = error_code
    bean.error!!.message = error_message!!
    return bean
}

private fun executeUpload2(requestBean: UploadImageRequestBean): UpLoadImgResponseBean {

    val error_code: Int
    val error_message: String?

    val path = "${AYApplication.getAppContext().externalCacheDir.toString()}/crop_image.jpg"

    val time = DateUtils.currentFixedSkewedTimeInRFC822Format()

    //        String imgUUID = CalUtils.getUUID32();

    val imageName = requestBean.imgUUID + ".jpg"

    val sb = StringBuilder()
    sb.append("PUT\n")
            .append("\n")
            .append("image/jpeg\n")
            .append(time + "\n")
            .append("x-oss-security-token:" + AYPrefUtils.getSecurityToken() + "\n")
            .append("/bm-dongda/")
            .append(imageName)

    val signature = OSSUtils.sign(AYPrefUtils.getAccesskeyId(), AYPrefUtils.getAccesskeySecret(), sb.toString())

    LogUtils.d("xcx", "onClick: ziji \n$signature")
    LogUtils.d("xcx", "onClick: ziji content \n" + sb.toString())

    var requestBuilder = Request.Builder()

    requestBuilder.url("https://bm-dongda.oss-cn-beijing.aliyuncs.com/$imageName")
    requestBuilder.addHeader("Host", "bm-dongda.oss-cn-beijing.aliyuncs.com")
    requestBuilder.addHeader("Date", time)
    requestBuilder.addHeader("Content-Type", "image/jpeg")
    requestBuilder.addHeader("User-Agent", "okhttp/3.10.0")
    requestBuilder.addHeader("x-oss-security-token", AYPrefUtils.getSecurityToken())
    requestBuilder.addHeader("Authorization", signature)

    val file = File(path)
    if (requestBean.userIconData == null || requestBean.userIconData.size == 0) {
        val body = RequestBody.create(MediaType.parse("image/jpeg"), file)
        requestBuilder = requestBuilder.method("PUT", body)
    } else {
        val body = RequestBody.create(MediaType.parse("image/jpeg"), requestBean.userIconData)
        requestBuilder = requestBuilder.method("PUT", body)
    }

    try {
        val response = BaseApi.httpClient.newCall(requestBuilder.build()).execute()
        val bean = UpLoadImgResponseBean()
        if (response.isSuccessful) {
            bean.status = "ok"
            bean.img_uuid = requestBean.imgUUID

        } else {
            val errorBean = BaseResponseBean.ErrorBean()
            errorBean.code = response.code()
            errorBean.message = response.message()
            bean.error = errorBean

        }
        response.close()
        return bean

    } catch (e1: ConnectTimeoutException) {
        error_code = DataConstant.CONNECT_TIMEOUT_EXCEPTION
        error_message = e1.message
        LogUtils.e(UpLoadWeChatIconApi::class.java, "ConnectTimeoutException: ", e1)

    } catch (e2: SocketTimeoutException) {//服务器响应超时
        error_code = DataConstant.SOCKET_TIMEOUT_EXCEPTION
        error_message = e2.message
        LogUtils.e(UpLoadWeChatIconApi::class.java, "SocketTimeoutException: ", e2)

    } catch (e3: ConnectException) {//服务器请求超时
        error_code = DataConstant.CONNECT_EXCEPTION
        error_message = e3.message
        LogUtils.e(UpLoadWeChatIconApi::class.java, "ConnectException: ", e3)

    } catch (e4: Exception) {
        error_code = DataConstant.OTHER_EXCEPTION
        error_message = e4.message
        LogUtils.e(UpLoadWeChatIconApi::class.java, "Exception: ", e4)

    }

    return getUploadErrorData2(error_code, error_message)

}

private fun getUploadErrorData2(error_code: Int, error_message: String?): UpLoadImgResponseBean {
    val bean = UpLoadImgResponseBean()
    val errorBean = BaseResponseBean.ErrorBean()
    errorBean.code = error_code
    errorBean.message = error_message
    bean.error = errorBean

    return bean
}