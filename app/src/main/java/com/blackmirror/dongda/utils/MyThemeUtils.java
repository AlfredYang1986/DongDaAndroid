package com.blackmirror.dongda.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * MyTabLayout用到 暂时不用修改
 * Create By Ruge at 2018-09-27
 */
public class MyThemeUtils {
    private static final int[] APPCOMPAT_CHECK_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimary
    };

    public static void checkAppCompatTheme(Context context) {
        TypedArray a = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        final boolean failed = !a.hasValue(0);
        a.recycle();
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme "
                    + "(or descendant) with the design library.");
        }
    }
}
