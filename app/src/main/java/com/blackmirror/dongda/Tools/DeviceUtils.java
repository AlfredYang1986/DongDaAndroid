package com.blackmirror.dongda.Tools;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.blackmirror.dongda.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * Created by xcx on 2018/4/15.
 */

public class DeviceUtils {


    public static final String ROM_MIUI = "MIUI";
    public static final String ROM_EMUI = "EMUI";
    public static final String ROM_FLYME = "FLYME";
    public static final String ROM_OPPO = "OPPO";
    public static final String ROM_SMARTISAN = "SMARTISAN";
    public static final String ROM_VIVO = "VIVO";
    public static final String ROM_QIKU = "QIKU";

    private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
    private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";


    /**
     * 是否是小米设备
     * @return
     */
    public static boolean isMIUI() {
        return "Xiaomi".equals(Build.MANUFACTURER) ||
                !TextUtils.isEmpty(getSystemProperty(KEY_VERSION_MIUI));
    }

    /**
     * 是否是魅族设备
     * @return
     */
    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    public static boolean isEMUI() {
        return TextUtils.isEmpty(getSystemProperty(KEY_VERSION_EMUI));
    }


    public static boolean isVIVO() {
        return TextUtils.isEmpty(getSystemProperty(KEY_VERSION_VIVO));
    }

    public static boolean isOPPO() {
        return TextUtils.isEmpty(getSystemProperty(KEY_VERSION_OPPO));
    }


    public static boolean is360() {
        return TextUtils.isEmpty(getSystemProperty(ROM_QIKU)) || TextUtils.isEmpty(getSystemProperty("360"));
         }

    public static boolean isSmartisan() {
        return TextUtils.isEmpty(getSystemProperty(KEY_VERSION_SMARTISAN));
    }


    public static void gotoPermissionSetting(AppCompatActivity activity){
        if (isMIUI()){
            gotoMIUISetting(activity);
        }else if (isFlyme()){
            gotoFlymeSetting(activity);
        }
    }

    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            java.lang.Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        LogUtils.d("xcx","line=="+line);
        return line;
    }

    public static void gotoMIUISetting(AppCompatActivity activity){
        try {
            // MIUI 9
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", activity.getPackageName());
            activity.startActivity(localIntent);
        }catch (Exception e3) {
            try {
                // MIUI 8
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");

                localIntent.putExtra("extra_pkgname", activity.getPackageName());
                activity.startActivity(localIntent);
            } catch (Exception e2) {
                try {
                    // MIUI 5/6/7
                    Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    localIntent.putExtra("extra_pkgname", activity.getPackageName());
                    activity.startActivity(localIntent);
                } catch (Exception e1) {
                    // 否则跳转到应用详情
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                }
            }
        }
    }
    /**
     * 跳转到魅族的权限管理系统
     */
    public static void gotoFlymeSetting(AppCompatActivity activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
//            gotoHuaweiPermission();
        }
    }
}
