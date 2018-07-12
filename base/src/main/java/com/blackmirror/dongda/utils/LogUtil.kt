package com.blackmirror.dongda.utils

import android.content.pm.ApplicationInfo
import android.util.Log
import com.blackmirror.dongda.base.AYApplication

/**
 * Create By Ruge at 2018-07-02
 */
var DEBUG = AYApplication.getApplication().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
const val DEFAULT_TAG = "xcx"
const val DEFAULT_ERROR_MSG = "dongda Exception: "


fun logD(TAG: String = DEFAULT_TAG, message: String = DEFAULT_ERROR_MSG, t: Throwable? = null) {
    if (!DEBUG) {
        return
    }
    if (t == null) {
        Log.d(TAG, message)
    } else {
        Log.d(TAG, message, t)
    }
}

fun logE(TAG: String = DEFAULT_TAG, message: String = DEFAULT_ERROR_MSG, t: Throwable? = null) {
    if (!DEBUG)
        return
    if (t == null) {
        Log.e(TAG, message)
    } else {
        Log.e(TAG, message, t)
    }
}