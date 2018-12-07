package com.blackmirror.dongda.utils

import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

/**
 * Create By Ruge at 2018-12-07
 */
fun toBase64String(binaryData: ByteArray?): String {
    return String(Base64.encode(binaryData, Base64.DEFAULT))
}

/**
 * decode base64 string
 */
fun fromBase64String(base64String: String): ByteArray {
    return Base64.decode(base64String, Base64.DEFAULT)
}

/**
 * calculate md5 for bytes
 */
fun calculateMd5(binaryData: ByteArray): ByteArray {
    var messageDigest: MessageDigest? = null
    try {
        messageDigest = MessageDigest.getInstance("MD5")
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException("MD5 algorithm not found.")
    }

    messageDigest!!.update(binaryData)
    return messageDigest.digest()

}

/**
 * calculate md5 for local file
 */
@Throws(IOException::class)
fun calculateMd5(filePath: String): ByteArray {
    val md5: ByteArray
    try {
        val digest = MessageDigest.getInstance("MD5")
        val buffer = ByteArray(10 * 1024)
        val fis = FileInputStream(File(filePath))
        var len=0

//        while ((len = fis.read(buffer)) != -1) {
//            digest.update(buffer, 0, len)
//        }

        while (fis.read(buffer).let {
                    len=it
                    it!=-1 }){
            digest.update(buffer, 0, len)
        }

        fis.close()
        md5 = digest.digest()
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException("MD5 algorithm not found.")
    }

    return md5
}

/**
 * calculate md5 for bytes and string back
 */
fun calculateMd5Str(binaryData: ByteArray): String {
    return getMd5StrFromBytes(calculateMd5(binaryData))
}

/**
 * calculate md5 for file and string back
 */
@Throws(IOException::class)
fun calculateMd5Str(filePath: String): String {
    return getMd5StrFromBytes(calculateMd5(filePath))
}

/**
 * calculate md5 for bytes and base64 string back
 */
fun calculateBase64Md5(binaryData: ByteArray): String {
    return toBase64String(calculateMd5(binaryData))
}

/**
 * calculate md5 for local file and base64 string back
 */
@Throws(IOException::class)
fun calculateBase64Md5(filePath: String): String {
    return toBase64String(calculateMd5(filePath))
}

/**
 * MD5sum for string
 */
fun getMd5StrFromBytes(md5bytes: ByteArray?): String {
    if (md5bytes == null) {
        return ""
    }
    val sb = StringBuilder()
    for (i in md5bytes.indices) {
        sb.append(String.format("%02x", md5bytes[i]))
    }
    return sb.toString()
}

/**
 * Get the sha1 value of the filepath specified file
 *
 * @param filePath The filepath of the file
 * @return The sha1 value
 */
fun fileToSHA1(filePath: String): String? {
    var inputStream: InputStream? = null
    try {
        inputStream = FileInputStream(filePath) // Create an FileInputStream instance according to the filepath
        val buffer = ByteArray(1024) // The buffer to read the file
        val digest = MessageDigest.getInstance("SHA-1") // Get a SHA-1 instance
        var numRead = 0 // Record how many bytes have been read
        while (numRead != -1) {
            numRead = inputStream.read(buffer)
            if (numRead > 0) {
                digest.update(buffer, 0, numRead) // Update the digest
            }
        }
        val sha1Bytes = digest.digest() // Complete the hash computing
        return convertHashToString(sha1Bytes) // Call the function to convert to hex digits
    } catch (e: Exception) {
        return null
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close() // Close the InputStream
            } catch (e: Exception) {
            }

        }
    }
}

/**
 * Convert the hash bytes to hex digits string
 *
 * @param hashBytes
 * @return The converted hex digits string
 */
private fun convertHashToString(hashBytes: ByteArray): String {
    var returnVal = ""
    for (i in hashBytes.indices) {
        returnVal += Integer.toString((hashBytes[i] and 0xff.toByte()) + 0x100, 16).substring(1)
    }
    return returnVal.toLowerCase()
}