package com.blackmirror.dongda.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast;

    private ToastUtils() {
    }

    private static void getToast() {
        if (toast == null) {
            toast = Toast.makeText(AYApplication.getAppContext(),"",Toast.LENGTH_SHORT);
        }
    }

    public static void showShortToast(CharSequence msg) {
        showToast(AYApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context context,CharSequence msg) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(int resId) {
        showToast(AYApplication.getAppContext(), resId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(CharSequence msg) {
        showToast(AYApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    private static void showToast(final Context context, final CharSequence msg, int duration) {
        try {
            getToast();
            toast.setText(msg);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            LogUtils.e(context.getClass(),"Exception==", e);
        }
    }

    private static void showToast(Context context, int resId, int duration) {
        try {
            if (resId == 0) {
                return;
            }
            getToast();
            toast.setText(resId);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            LogUtils.e(context.getClass(),"Exception==", e);
        }
    }
}
