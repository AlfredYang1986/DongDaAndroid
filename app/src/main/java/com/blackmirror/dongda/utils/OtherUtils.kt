package com.blackmirror.dongda.utils

import android.content.Context
import android.net.Uri

/**
 * Create By Ruge at 2018-12-07
 */
private val ANDROID_RESOURCE = "android.resource://"
private val FOREWARD_SLASH = "/"

fun resourceIdToUri(context: Context, resourceId: Int): Uri {
    return Uri.parse(ANDROID_RESOURCE + context.packageName + FOREWARD_SLASH + resourceId)
}