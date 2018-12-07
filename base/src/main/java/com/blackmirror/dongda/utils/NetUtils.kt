package com.blackmirror.dongda.utils

import android.content.Context
import android.net.ConnectivityManager
import com.blackmirror.dongda.base.AYApplication
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Create By Ruge at 2018-12-06
 */

/**
 * 检查网络是否可用
 *
 * @return
 */
fun isNetworkAvailable(): Boolean {
    return isNetworkAvailable(AYApplication.appContext)
}

/**
 * 检查网络是否可用
 *
 * @param context
 * @return
 */
fun isNetworkAvailable(context: Context): Boolean {

    val manager = context.applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false

    val networkinfo = manager.activeNetworkInfo

    return if (networkinfo == null || !networkinfo.isAvailable) {
        false
    } else true

}

/**
 * Encode a URL segment with special chars replaced.
 *
 * @param value
 * @return
 * @throws UnsupportedEncodingException
 */
// TODO change the method name to percentageEncode
fun urlEncode(value: String?, encoding: String): String {
    if (value == null) {
        return ""
    }

    try {
        val encoded = URLEncoder.encode(value, encoding)
        // a b*c~d/e+f  经过URLEncoder.encode 会 转变成 a+b*c%7Ed%2Fe%2Bf
        return encoded.replace("+", "%20").replace("*", "%2A")
                .replace("%7E", "~").replace("%2F", "/")
    } catch (e: Exception) {
        throw IllegalArgumentException("failed to encode url!", e)
    }

}

/**
 * Encode request parameters to URL segment.
 */
fun paramToQueryString(params: Map<String, String>?, charset: String): String? {

    if (params == null || params.isEmpty()) {
        return null
    }

    val paramString = StringBuilder()
    var first = true
    for ((key, value) in params) {

        if (!first) {
            paramString.append("&")
        }

        // Urlencode each request parameter
        paramString.append(urlEncode(key, charset))
        if (value != null) {
            paramString.append("=").append(urlEncode(value, charset))
        }

        first = false
    }

    return paramString.toString()
}