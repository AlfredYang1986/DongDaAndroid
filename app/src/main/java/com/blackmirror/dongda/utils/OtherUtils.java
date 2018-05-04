package com.blackmirror.dongda.utils;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Ruge on 2018-04-04 下午12:19
 */
public class OtherUtils {
    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }


}
