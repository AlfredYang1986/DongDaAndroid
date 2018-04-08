package com.blackmirror.dongda.Tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by alfredyang on 30/6/17.
 */

public class AYScreenSingleton extends Object {
    /**
     7.     * 单例对象实例
     8.     */
    private static AYScreenSingleton instance = null;

    private float screenWidth = 0;
    private float screenHeight = 0;
    private float screenDensity = 0;

    public synchronized static AYScreenSingleton getInstance() {
        if (instance == null) {
            instance = new AYScreenSingleton();
        }
        return instance;
    }

    public float getScreenWidth(Context context) {
        checkAlreadyInvoke(context);
        return screenWidth;
    }

    public float getScreenHeight(Context context) {
        checkAlreadyInvoke(context);
        return screenHeight;
    }

    public float getScreenDensity(Context context) {
        checkAlreadyInvoke(context);
        return screenDensity;
    }

    private void checkAlreadyInvoke(Context context) {
        if (screenDensity == 0 || screenWidth == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            float width = dm.widthPixels;         // 屏幕宽度（像素）
            float height = dm.heightPixels;       // 屏幕高度（像素）
            float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）

            // 屏幕宽度:屏幕宽度（像素）/屏幕密度
            screenWidth = width / density;  // 屏幕宽度(dp)
            screenHeight = height / density;// 屏幕高度(dp)
            screenDensity = density;
        }
    }
}
