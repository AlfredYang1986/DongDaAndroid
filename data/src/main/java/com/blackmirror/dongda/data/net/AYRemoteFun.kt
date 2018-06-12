package com.blackmirror.dongda.data.net

import com.alibaba.fastjson.JSON
import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.request.BaseRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.utils.LogUtils
import io.reactivex.Observable
import okhttp3.*
import org.apache.http.conn.ConnectTimeoutException
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import java.util.zip.GZIPInputStream

/**
 * Create By Ruge at 2018-06-06
 */
val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()


inline fun <P : BaseResponseBean, Q : BaseRequestBean> execute(q: Q, myClass: Class<P>): Observable<P> {
    return Observable.just(q).map { q ->
        val request: Request = Request.Builder()
                .url(q.url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), q.json.toString()))
                .build()
        executeRequest2(request, myClass)
    }
}

fun <P : BaseResponseBean> executeRequest2(request: Request, myClass: Class<P>): P {
    //        LogUtils.d("flag","做网络请求前的json数据: "+args.toString());
    var error_code: Int?
    var error_message: String?

    try {

        val response = httpClient.newCall(request).execute()
        val input = inputStreamAfterCheck(response)
        val iReader = InputStreamReader(input)
        val bReader = BufferedReader(iReader)
        val json = StringBuilder()
        bReader.lineSequence().forEach {
            json.append(it)
        }
        bReader.close()
        iReader.close()
        input.close()
        LogUtils.d("flag", "返回的数据：" + json.toString())


        var obj: P = JSON.parseObject(json.toString(), myClass)

        return obj
    } catch (e1: ConnectTimeoutException) {
        error_code = DataConstant.CONNECT_TIMEOUT_EXCEPTION
        error_message = e1.message
//        LogUtils.e(AYRemoteFun::class.java, "ConnectTimeoutException: ", e1)

    } catch (e2: SocketTimeoutException) {//服务器响应超时
        error_code = DataConstant.SOCKET_TIMEOUT_EXCEPTION
        error_message = e2.message
//        LogUtils.e(AYRemoteApi.class, "SocketTimeoutException: ", e2);

    } catch (e3: ConnectException) {//服务器请求超时
        error_code = DataConstant.CONNECT_EXCEPTION
        error_message = e3.message
//        LogUtils.e(AYRemoteApi.class, "ConnectException: ", e3);

    } catch (e4: Exception) {
        error_code = DataConstant.OTHER_EXCEPTION
        error_message = e4.message
//        LogUtils.e(AYRemoteApi.class, "Exception: ", e4);

    }
    var obj: P? = null

    try {
        obj = myClass.newInstance()
        obj.error = BaseResponseBean.ErrorBean()
        obj.error!!.code = error_code!!
        obj.error!!.message = error_message!!
    } catch (e: InstantiationException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
    return obj!!
}

fun inputStreamAfterCheck(response: Response): InputStream {
    var input: InputStream = response.body()!!.byteStream()
    if ("gzip".equals(response.header("Content-Encoding"), true)) {
        input = GZIPInputStream(input)
    }
    return input
}