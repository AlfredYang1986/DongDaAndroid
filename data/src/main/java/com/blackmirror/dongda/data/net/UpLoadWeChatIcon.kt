package com.blackmirror.dongda.data.net

import com.blackmirror.dongda.data.*
import com.blackmirror.dongda.data.model.request.UploadImageRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.data.model.response.DownloadWeChatIconResponseBean
import com.blackmirror.dongda.data.model.response.OssInfoResponseBean
import com.blackmirror.dongda.data.model.response.UpLoadImgResponseBean
import com.blackmirror.dongda.utils.AYPrefUtils
import com.blackmirror.dongda.utils.DateUtils
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.apache.http.conn.ConnectTimeoutException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by xcx on 2018/6/11.
 */

fun upload(requestBean: UploadImageRequestBean, myClass: Class<UpLoadImgResponseBean>): Observable<UpLoadImgResponseBean> {

    return Observable.just(requestBean)
            .map {
                if (DateUtils.isNeedRefreshToken(AYPrefUtils.getExpiration())) {
                    val json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\"}"
                    val request = Request.Builder()
                            .url(OSS_INFO_URL).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)).build()
                    val bean = executeRequest(request, OssInfoResponseBean::class.java)

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
                getIcon(request)
            }.map { bean ->
                if ("ok" == bean.status) {
                    requestBean.userIconData = bean.userIcon
                    executeUpload(requestBean)
                } else {
                    val code = if (bean.error == null) NET_UNKNOWN_ERROR else bean.error!!.code
                    //                            String message = bean.error == null ? "" : bean.error.message;
                    val message = "获取微信头像失败"
                    getUploadErrorData(code, message)
                }
            }
}


private fun getIcon(request: Request): DownloadWeChatIconResponseBean {
    //        LogUtils.d("flag","做网络请求前的json数据: "+args.toString());
    val error_code: Int
    val error_message: String?
    val bean = DownloadWeChatIconResponseBean()
    try {
        val response = httpClient.newCall(request).execute()
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
        error_code = CONNECT_TIMEOUT_EXCEPTION
        error_message = e1.message
//        LogUtils.e(AYRemoteApi::class.java, "ConnectTimeoutException: ", e1)

    } catch (e2: SocketTimeoutException) {//服务器响应超时
        error_code = SOCKET_TIMEOUT_EXCEPTION
        error_message = e2.message
//        LogUtils.e(AYRemoteApi::class.java, "SocketTimeoutException: ", e2)

    } catch (e3: ConnectException) {//服务器请求超时
        error_code = CONNECT_EXCEPTION
        error_message = e3.message
//        LogUtils.e(AYRemoteApi::class.java, "ConnectException: ", e3)

    } catch (e4: Exception) {
        error_code = OTHER_EXCEPTION
        error_message = e4.message
//        LogUtils.e(AYRemoteApi::class.java, "Exception: ", e4)

    }

    bean.error = BaseResponseBean.ErrorBean()
    bean.error!!.code = error_code
    bean.error!!.message = error_message!!
    return bean
}
