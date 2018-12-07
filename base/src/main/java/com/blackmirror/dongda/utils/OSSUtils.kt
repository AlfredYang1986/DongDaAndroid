package com.blackmirror.dongda.utils

/**
 * Create By Ruge at 2018-12-07
 */
val DEFAULT_CHARSET_NAME = "utf-8"


fun getSignedUrl(imgUrl: String?, time: Long=30*60): String {

    if (imgUrl.isNullOrEmpty()){
        return ""
    }

    val t = System.currentTimeMillis() / 1000 + time
    val verb = "GET\n"
    val Content_MD5 = "\n"
    val Content_Type = "\n"
    val Date = t.toString() + "\n"
    val ss = "/bm-dongda/$imgUrl.jpg?security-token="


    val sb = StringBuilder()
    sb.append(verb)
            .append(Content_MD5)
            .append(Content_Type)
            .append(Date)
            .append(ss)
            .append(getSecurityToken())
    val sign = sign(getAccesskeySecret(), sb.toString())
    val sb2 = StringBuilder()

    sb2.append("https://bm-dongda.oss-cn-beijing.aliyuncs.com/" + imgUrl + ".jpg?Expires=".trim())
            .append(t)
            .append("&OSSAccessKeyId=".trim())
            .append(getAccesskeyId())
            .append("&Signature=")
            .append(urlEncode(sign, DEFAULT_CHARSET_NAME))
            .append("&security-token=")
            .append(urlEncode(getSecurityToken(), DEFAULT_CHARSET_NAME))
    return sb2.toString()
}

/**
 * 根据ak/sk、content生成token
 *
 * @param screctKey
 * @param content
 * @return
 */
private fun sign(screctKey: String, content: String): String {

    var signature = ""

    try {
        signature = HmacSHA1Signature().computeSignature(screctKey, content)
//        signature = signature.trim { it <= ' ' }
        signature = signature.trim()
    } catch (e: Exception) {
        //            throw new IllegalStateException("Compute signature failed!", e);
    }

    //        Log.d("xcx","signature "+signature);


    return signature
}

/**
 * 根据ak/sk、content生成token
 * 上传图片用 文件用
 * @param accessKey
 * @param secretKey
 * @param content
 * @return
 */
private fun sign(accessKey: String, secretKey: String, content: String): String {

    var signature = ""

    try {
        signature = HmacSHA1Signature().computeSignature(secretKey, content)
        signature = signature.trim { it <= ' ' }
    } catch (e: Exception) {
        //            throw new IllegalStateException("Compute signature failed!", e);
    }

    return "OSS $accessKey:$signature"
}