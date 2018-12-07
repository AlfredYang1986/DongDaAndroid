package com.blackmirror.dongda.utils

import com.blackmirror.dongda.base.AYApplication

/**
 * Create By Ruge at 2018-12-06
 */

fun px2dp(pxVal: Float): Float {
    val scale = AYApplication.appContext.resources.displayMetrics.density
    return pxVal / scale
}

/**
 * dp转px
 *
 * @param dp
 * @return
 */
fun dp2px(dp: Int): Int {
    val scale = AYApplication.appContext.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun getScreenWidthDp(): Int {
    //2、通过Resources获取
    val dm = AYApplication.appContext.resources.displayMetrics

    val density = dm.density

    /*// 屏幕宽度:屏幕宽度（像素）/屏幕密度
            screenWidth = width / density;  // 屏幕宽度(dp)
            screenHeight = height / density;// 屏幕高度(dp)
            screenDensity = density;*/
    return (dm.widthPixels / density).toInt()
}

fun getScreenWidthPx(): Int {
    //2、通过Resources获取
    val dm = AYApplication.appContext.resources.displayMetrics
    return dm.widthPixels
}

fun getScreenHeightDp(): Int {
    //2、通过Resources获取
    val dm = AYApplication.appContext.resources.displayMetrics

    val density = dm.density

    /*// 屏幕宽度:屏幕宽度（像素）/屏幕密度
                screenWidth = width / density;  // 屏幕宽度(dp)
                screenHeight = height / density;// 屏幕高度(dp)
                screenDensity = density;*/
    return (dm.heightPixels / density).toInt()
}