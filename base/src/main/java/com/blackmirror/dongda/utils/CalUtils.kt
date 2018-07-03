package com.blackmirror.dongda.utils

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.experimental.and

/**
 * Create By Ruge at 2018-07-03
 */
/**
 * 求最大公约数数
 * @param a
 * @param b
 * @return
 */
fun getGongyue(a: Int, b: Int): Int {
    var a = a
    var b = b
    var gongyue = 0
    if (a < b) {   //交换a、b的值
        a = a + b
        b = a - b
        a = a - b
    }
    if (a % b == 0) {
        gongyue = b
    }
    while (a % b > 0) {
        a = a % b
        if (a < b) {
            a = a + b
            b = a - b
            a = a - b
        }
        if (a % b == 0) {
            gongyue = b
        }
    }
    return gongyue
}

/**
 * 获取MD5
 * @param seed
 * @return
 */
fun md5(seed: String): String {
    var hash: ByteArray? = null

    try {
        hash = MessageDigest.getInstance("MD5").digest(seed.toByteArray(charset("UTF-8")))
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    }

    val hex = StringBuilder(hash!!.size * 2)
    for (b in hash) {
        if (b and 0xFF.toByte() < 0x10)
            hex.append("0")
        hex.append(Integer.toHexString((b and 0xFF.toByte()).toInt()))
    }

    return hex.toString()
}

/**
 * 获取32位UUID
 * @return
 */
fun getUUID32(): String {
    return UUID.randomUUID().toString().toLowerCase()
}