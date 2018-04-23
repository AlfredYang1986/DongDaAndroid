package com.blackmirror.dongda.Tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ruge on 2018-04-04 下午12:19
 */
public class OtherUtils {
    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context,int resourceId){
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }



    public static void initSystemBarColor(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT);
            View childView = viewGroup.getChildAt(0);
            if (null != childView) {
                ViewCompat.setFitsSystemWindows(childView, false);
            }
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param activity
     */
    public static void setStatusBarColor(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (DeviceUtils.isMIUI()){
                activity.getWindow().setStatusBarColor(Color.WHITE);
                MIUISetStatusBarLightMode(activity, true);
                return;
            }
            if (DeviceUtils.isFlyme()){
                activity.getWindow().setStatusBarColor(Color.WHITE);
                //                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                FlymeSetStatusBarLightMode(activity.getWindow(), true);
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = activity.getWindow();
                window.setStatusBarColor(Color.WHITE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                /*activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);*/
            }
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param activity
     */
    public static void setStatusBarColor(Activity activity,@ColorInt int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (DeviceUtils.isMIUI()){
                activity.getWindow().setStatusBarColor(color);
                MIUISetStatusBarLightMode(activity, false);
                return;
            }
            if (DeviceUtils.isFlyme()){
                activity.getWindow().setStatusBarColor(color);
                //                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                FlymeSetStatusBarLightMode(activity.getWindow(), false);
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = activity.getWindow();
                window.setStatusBarColor(color);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                /*activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);*/
            }
        }
    }



    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 需要MIUIV6以上
     * @param activity
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window=activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if(dark){
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
                }else{
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result=true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if(dark){
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                        activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                        int flag = window.getDecorView().getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                        window.getDecorView().setSystemUiVisibility(flag);
                    }
                }
            }catch (Exception e){

            }
        }
        return result;
    }



    public static float px2dp(float pxVal) {
        final float scale = AYApplication.appConext.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * dp转px
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float scale = AYApplication.appConext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getScreenWidthDp(){
        //2、通过Resources获取
        DisplayMetrics dm = AYApplication.appConext.getResources().getDisplayMetrics();

        float density = dm.density;
        int width = (int) (dm.widthPixels/density);

        /*// 屏幕宽度:屏幕宽度（像素）/屏幕密度
        screenWidth = width / density;  // 屏幕宽度(dp)
        screenHeight = height / density;// 屏幕高度(dp)
        screenDensity = density;*/
        return width;
    }

    public static int getScreenHeightDp(){
        //2、通过Resources获取
        DisplayMetrics dm = AYApplication.appConext.getResources().getDisplayMetrics();

        float density = dm.density;
        int height = (int) (dm.heightPixels/density);

        /*// 屏幕宽度:屏幕宽度（像素）/屏幕密度
        screenWidth = width / density;  // 屏幕宽度(dp)
        screenHeight = height / density;// 屏幕高度(dp)
        screenDensity = density;*/
        return height;
    }



    public static boolean isNeedRefreshToken(String token_time){
        return false;
    }

    public static long getRefreshTime(int second){
        return second;
    }

    public static long getRefreshTime(String expirate_time){
        long l = getExpirateTime(expirate_time) - System.currentTimeMillis() / 1000;
        if (l<=0){
            return 0;
        }
        return l/2;
    }

    public static long getExpirateTime(String expirate_time){
        long time;
        String date = expirate_time.replace("Z", " UTC");//注意是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");//注意格式化的表达式
        try {
            Date d = format.parse(date);
            time = d.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
            time=System.currentTimeMillis()/1000+300;
        }
        return time;
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
                //                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                //                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }



    public static void changeSysBarToTranslucent(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT);
            View childView = viewGroup.getChildAt(0);
            if (null != childView) {
                ViewCompat.setFitsSystemWindows(childView, false);
            }
        }
    }



}
