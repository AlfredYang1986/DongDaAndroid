package com.blackmirror.dongda.utils;

import android.util.DisplayMetrics;

/**
 * 像素Dp Px相关
 * Created by Ruge on 2018-05-03 下午4:46
 */
public class DensityUtils {

    public static float px2dp(float pxVal) {
        final float scale = AYApplication.appContext.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float scale = AYApplication.appContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getScreenWidthDp() {
        //2、通过Resources获取
        DisplayMetrics dm = AYApplication.appContext.getResources().getDisplayMetrics();

        float density = dm.density;
        int width = (int) (dm.widthPixels / density);

        /*// 屏幕宽度:屏幕宽度（像素）/屏幕密度
        screenWidth = width / density;  // 屏幕宽度(dp)
        screenHeight = height / density;// 屏幕高度(dp)
        screenDensity = density;*/
        return width;
    }

    public static int getScreenWidthPx() {
        //2、通过Resources获取
        DisplayMetrics dm = AYApplication.appContext.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeightDp() {
        //2、通过Resources获取
        DisplayMetrics dm = AYApplication.appContext.getResources().getDisplayMetrics();

        float density = dm.density;
        int height = (int) (dm.heightPixels / density);

        /*// 屏幕宽度:屏幕宽度（像素）/屏幕密度
        screenWidth = width / density;  // 屏幕宽度(dp)
        screenHeight = height / density;// 屏幕高度(dp)
        screenDensity = density;*/
        return height;
    }
}
