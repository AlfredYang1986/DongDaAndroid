package com.blackmirror.dongda.Tools;

import android.text.TextUtils;
import android.util.Log;

public class LogUtils {
    public static boolean SHOWLOG = true;
    public static final String DEFAULT_TAG = "dongda";
    public static final String DEFAULT_ERROR_MSG = "dongda exception: ";

    public static void d(String message) {
        d(DEFAULT_TAG, message);
    }


    public static void d(Class<?> c, String message) {
        String tag = (c == null ? DEFAULT_TAG : c.getSimpleName());
        d(tag, message);
    }

    public static void d(String TAG, String message) {
        d(TAG, message, null);
    }

    public static void d(String TAG, String message, Throwable t) {
        if (!SHOWLOG) {
            return;
        }
        if (TextUtils.isEmpty(TAG))
            TAG = DEFAULT_TAG;
        if (t == null) {
            Log.d(TAG, message);
        } else {
            Log.d(TAG, message, t);
        }
    }

    public static void e(Class<?> c, Throwable t){
        String tag = (c == null ? DEFAULT_TAG : c.getSimpleName());
        e(tag, DEFAULT_ERROR_MSG, t);
    }

    public static void e(Class<?> c, String message, Throwable t) {
        String tag = (c == null ? DEFAULT_TAG : c.getSimpleName());
        e(tag, message, t);
    }

    private static void e(String TAG, String message, Throwable t) {
        if (!SHOWLOG)
            return;
        if (TextUtils.isEmpty(TAG))
            TAG = DEFAULT_TAG;
        if (t == null) {
            Log.e(TAG, message);
        } else {
            Log.e(TAG, message, t);
        }
    }

}