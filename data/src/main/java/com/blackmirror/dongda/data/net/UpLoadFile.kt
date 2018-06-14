package com.blackmirror.dongda.data.net

import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.request.UploadImageRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.data.model.response.UpLoadImgResponseBean
import com.blackmirror.dongda.utils.AYPrefUtils
import com.blackmirror.dongda.utils.DateUtils
import com.blackmirror.dongda.utils.LogUtils
import com.blackmirror.dongda.utils.OSSUtils
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

 fun executeUpload(requestBean: UploadImageRequestBean): UpLoadImgResponseBean {

    val error_code: Int
    val error_message: String?

    val path = AYApplication.getAppContext().externalCacheDir!!.toString() + "/crop_image.jpg"

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
    val body = RequestBody.create(MediaType.parse("image/jpeg"), file)
    requestBuilder = requestBuilder.method("PUT", body)


    try {
        val response = httpClient.newCall(requestBuilder.build()).execute()
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
//        LogUtils.e(upload::class.java, "ConnectTimeoutException: ", e1)

    } catch (e2: SocketTimeoutException) {//服务器响应超时
        error_code = DataConstant.SOCKET_TIMEOUT_EXCEPTION
        error_message = e2.message
//        LogUtils.e(UpLoadFileApi::class.java, "SocketTimeoutException: ", e2)

    } catch (e3: ConnectException) {//服务器请求超时
        error_code = DataConstant.CONNECT_EXCEPTION
        error_message = e3.message
//        LogUtils.e(UpLoadFileApi::class.java, "ConnectException: ", e3)

    } catch (e4: Exception) {
        error_code = DataConstant.OTHER_EXCEPTION
        error_message = e4.message
//        LogUtils.e(UpLoadFileApi::class.java, "Exception: ", e4)

    }

    return getUploadErrorData(error_code, error_message)

}

fun getUploadErrorData(error_code: Int, error_message: String?): UpLoadImgResponseBean {
    val bean = UpLoadImgResponseBean()
    val errorBean = BaseResponseBean.ErrorBean()
    errorBean.code = error_code
    errorBean.message = error_message
    bean.error = errorBean

    return bean
}

