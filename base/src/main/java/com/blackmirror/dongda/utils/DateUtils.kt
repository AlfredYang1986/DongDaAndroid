package com.blackmirror.dongda.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create By Ruge at 2018-12-07
 */
// RFC 822 Date Format
private val RFC822_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'"
// ISO 8601 format
private val ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
// Alternate ISO 8601 format without fractional seconds
private val ALTERNATIVE_ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
@Volatile
private var amendTimeSkewed: Long = 0

/**
 * Formats Date to GMT string.
 *
 * @param date
 * @return
 */
fun formatRfc822Date(date: Date): String {
    return getRfc822DateFormat().format(date)
}

/**
 * Parses a GMT-format string.
 *
 * @param dateString
 * @return
 * @throws ParseException
 */
@Throws(ParseException::class)
fun parseRfc822Date(dateString: String): Date {
    return getRfc822DateFormat().parse(dateString)
}

private fun getRfc822DateFormat(): DateFormat {
    val rfc822DateFormat = SimpleDateFormat(RFC822_DATE_FORMAT, Locale.US)
    rfc822DateFormat.timeZone = SimpleTimeZone(0, "GMT")

    return rfc822DateFormat
}

fun formatIso8601Date(date: Date): String {
    return getIso8601DateFormat().format(date)
}

fun formatAlternativeIso8601Date(date: Date): String {
    return getAlternativeIso8601DateFormat().format(date)
}

/**
 * Parse a date string in the format of ISO 8601.
 *
 * @param dateString
 * @return
 * @throws ParseException
 */
@Throws(ParseException::class)
fun parseIso8601Date(dateString: String): Date {
    try {
        return getIso8601DateFormat().parse(dateString)
    } catch (e: ParseException) {
        return getAlternativeIso8601DateFormat().parse(dateString)
    }

}

private fun getIso8601DateFormat(): DateFormat {
    val df = SimpleDateFormat(ISO8601_DATE_FORMAT, Locale.US)
    df.timeZone = SimpleTimeZone(0, "GMT")

    return df
}

private fun getAlternativeIso8601DateFormat(): DateFormat {
    val df = SimpleDateFormat(ALTERNATIVE_ISO8601_DATE_FORMAT, Locale.US)
    df.timeZone = SimpleTimeZone(0, "GMT")

    return df
}

fun getFixedSkewedTimeMillis(): Long {
    return System.currentTimeMillis() + amendTimeSkewed
}

@Synchronized
fun currentFixedSkewedTimeInRFC822Format(): String {
    return formatRfc822Date(Date(getFixedSkewedTimeMillis()))
}

@Synchronized
fun setCurrentServerTime(serverTime: Long) {
    amendTimeSkewed = serverTime - System.currentTimeMillis()
}

fun isNeedRefreshToken(expirate_time: String): Boolean {
    val time: Long
    val date = expirate_time.replace("Z", " UTC")//注意是空格+UTC
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z")//注意格式化的表达式
    try {
        val d = format.parse(date)
        time = d.time / 1000 - System.currentTimeMillis() / 1000

        return time <= 300

    } catch (e: ParseException) {

    }

    return true
}