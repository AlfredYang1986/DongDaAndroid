/**
 * Copyright (C) Alibaba Cloud Computing, 2015
 * All rights reserved.
 *
 *
 * 版权所有 （C）阿里巴巴云计算，2015
 */

package com.blackmirror.dongda.utils

import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Hmac-SHA1 signature
 */
class HmacSHA1Signature {

    val algorithm: String
        get() = ALGORITHM

    val version: String
        get() = VERSION

    fun computeSignature(key: String, data: String): String {
        var sign:String
        try {
            val signData = sign(
                    key.toByteArray(charset(DEFAULT_ENCODING)),
                    data.toByteArray(charset(DEFAULT_ENCODING)))

            sign = toBase64String(signData)
        } catch (ex: UnsupportedEncodingException) {
            throw RuntimeException("Unsupported algorithm: $DEFAULT_ENCODING")
        }

        return sign
    }


    private fun sign(key: ByteArray, data: ByteArray): ByteArray? {
        var sign: ByteArray? = null
        try {
            // Because Mac.getInstance(String) calls a synchronized method,
            // it could block on invoked concurrently.
            // SO use prototype pattern to improve perf.
            if (macInstance == null) {
                synchronized(LOCK) {
                    if (macInstance == null) {
                        macInstance = Mac.getInstance(algorithm)
                    }
                }
            }

            var mac: Mac
            try {
                mac = macInstance!!.clone() as Mac
            } catch (e: CloneNotSupportedException) {
                // If it is not clonable, create a new one.
                mac = Mac.getInstance(algorithm)
            }

            mac.init(SecretKeySpec(key, algorithm))
            sign = mac.doFinal(data)
        } catch (ex: NoSuchAlgorithmException) {
            throw RuntimeException("Unsupported algorithm: $ALGORITHM")
        } catch (ex: InvalidKeyException) {
            throw RuntimeException("key must not be null")
        }

        return sign
    }

    companion object {
        private val DEFAULT_ENCODING = "UTF-8" // Default encoding
        private val ALGORITHM = "HmacSHA1" // Signature method.
        private val VERSION = "1" // Signature version.
        private val LOCK = Any()
        private var macInstance: Mac? = null // Prototype of the Mac instance.
    }
}