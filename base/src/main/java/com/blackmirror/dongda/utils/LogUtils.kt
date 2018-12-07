package com.blackmirror.dongda.utils

import android.content.pm.ApplicationInfo
import android.util.Log
import com.blackmirror.dongda.base.AYApplication

/**
 * Create By Ruge at 2018-12-07
 */

var DEBUG = AYApplication.me.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
const val DEFAULT_TAG = "xcx"
const val DEFAULT_ERROR_MSG = "dongda Exception: "

@JvmOverloads
fun logD(message: String, TAG: String = DEFAULT_TAG, t: Throwable? = null) {
    if (!DEBUG) {
        return
    }
    if (t == null) {
        Log.d(TAG, message)
    } else {
        Log.d(TAG, message, t)
    }
}

fun logE(TAG: String = DEFAULT_TAG, message: String, exception: Throwable? = null) {
    if (!DEBUG)
        return
    if (exception == null) {
        Log.e(TAG, message)
    } else {
        Log.e(TAG, message, exception)
    }
}