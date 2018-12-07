package com.blackmirror.dongda.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.ColorInt
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.blackmirror.dongda.BuildConfig
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Create By Ruge at 2018-12-07
 */
private val ROM_MIUI = "MIUI"
private val ROM_EMUI = "EMUI"
private val ROM_FLYME = "FLYME"
private val ROM_OPPO = "OPPO"
private val ROM_SMARTISAN = "SMARTISAN"
private val ROM_VIVO = "VIVO"
private val ROM_QIKU = "QIKU"

private val KEY_VERSION_MIUI = "ro.miui.ui.version.name"
private val KEY_VERSION_EMUI = "ro.build.version.emui"
private val KEY_VERSION_OPPO = "ro.build.version.opporom"
private val KEY_VERSION_SMARTISAN = "ro.smartisan.version"
private val KEY_VERSION_VIVO = "ro.vivo.os.version"


/**
 * 是否是小米设备
 *
 * @return
 */
fun isMIUI(): Boolean {
    return "Xiaomi" == Build.MANUFACTURER || !getSystemProperty(KEY_VERSION_MIUI).isNullOrEmpty()
}

/**
 * 是否是魅族设备
 *
 * @return
 */
fun isFlyme(): Boolean {
    try {
        val method = Build::class.java.getMethod("hasSmartBar")
        return method != null
    } catch (e: Exception) {
        return false
    }

}

fun isEMUI(): Boolean {
    return TextUtils.isEmpty(getSystemProperty(KEY_VERSION_EMUI))
}

fun isVIVO(): Boolean {
    return TextUtils.isEmpty(getSystemProperty(KEY_VERSION_VIVO))
}

fun isOPPO(): Boolean {
    return TextUtils.isEmpty(getSystemProperty(KEY_VERSION_OPPO))
}

fun is360(): Boolean {
    return TextUtils.isEmpty(getSystemProperty(ROM_QIKU)) || TextUtils.isEmpty(getSystemProperty("360"))
}

fun isSmartisan(): Boolean {
    return TextUtils.isEmpty(getSystemProperty(KEY_VERSION_SMARTISAN))
}

fun gotoPermissionSetting(activity: AppCompatActivity) {
    if (isMIUI()) {
        gotoMIUIPermissionSetting(activity)
    } else if (isFlyme()) {
        gotoFlymePermissionSetting(activity)
    } else {
        gotoHuaWeiPermissionSetting(activity)
    }
}

fun gotoMIUIPermissionSetting(activity: AppCompatActivity) {
    try {
        // MIUI 9
        val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
        localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
        localIntent.putExtra("extra_pkgname", activity.packageName)
        activity.startActivity(localIntent)
    } catch (e3: Exception) {
        try {
            // MIUI 8
            val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")

            localIntent.putExtra("extra_pkgname", activity.packageName)
            activity.startActivity(localIntent)
        } catch (e2: Exception) {
            try {
                // MIUI 5/6/7
                val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
                localIntent.putExtra("extra_pkgname", activity.packageName)
                activity.startActivity(localIntent)
            } catch (e1: Exception) {
                // 否则跳转到应用详情
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)
            }

        }

    }

}

/**
 * 跳转到魅族的权限管理系统
 */
fun gotoFlymePermissionSetting(activity: AppCompatActivity) {
    val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
    intent.addCategory(Intent.CATEGORY_DEFAULT)
    intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
    try {
        activity.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        gotoHuaWeiPermissionSetting(activity)
    }

}

/**
 * 华为的权限管理页面
 */
fun gotoHuaWeiPermissionSetting(activity: AppCompatActivity) {
    try {
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val comp = ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity")//华为权限管理
        intent.component = comp
        activity.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        activity.startActivity(getAppDetailSettingIntent(activity))
    }

}

/**
 * 获取应用详情页面intent
 *
 * @return
 */
fun getAppDetailSettingIntent(activity: AppCompatActivity): Intent {
    val localIntent = Intent()
    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    if (Build.VERSION.SDK_INT >= 9) {
        localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        localIntent.data = Uri.fromParts("package", activity.packageName, null)
    } else if (Build.VERSION.SDK_INT <= 8) {
        localIntent.action = Intent.ACTION_VIEW
        localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
        localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.packageName)
    }
    return localIntent
}

private fun getSystemProperty(propName: String): String? {
    val line: String
    var input: BufferedReader? = null
    try {
        val p = Runtime.getRuntime().exec("getprop $propName")
        input = BufferedReader(InputStreamReader(p.inputStream), 1024)
        line = input.readLine()
        input.close()
    } catch (ex: IOException) {
        return null
    } finally {
        if (input != null) {
            try {
                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
    return line
}

fun initSystemBarColor(activity: AppCompatActivity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val viewGroup = activity.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val childView = viewGroup.getChildAt(0)
        if (null != childView) {
            //                ViewCompat.setFitsSystemWindows(childView, false);
        }
    }
}

fun initSystemBarColor(activity: AppCompatActivity, FitsSys: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val viewGroup = activity.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val childView = viewGroup.getChildAt(0)
        if (null != childView) {
            ViewCompat.setFitsSystemWindows(childView, FitsSys)
        }
    }
}

/**
 * 修改状态栏颜色，支持4.4以上版本
 *
 * @param activity
 */
fun setStatusBarColor(activity: Activity) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (isMIUI()) {
            activity.window.statusBarColor = Color.WHITE
            MIUISetStatusBarLightMode(activity, true)
            return
        }
        if (isFlyme()) {
            activity.window.statusBarColor = Color.WHITE
            //                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            FlymeSetStatusBarLightMode(activity.window, true)
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = activity.window
            window.statusBarColor = Color.WHITE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            /*activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);*/
        }
    }
}

/**
 * 修改状态栏颜色，支持4.4以上版本
 *
 * @param activity
 */
fun setStatusBarColor(activity: Activity, @ColorInt color: Int) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (isMIUI()) {
            activity.window.statusBarColor = color
            MIUISetStatusBarLightMode(activity, true)
            return
        }
        if (isFlyme()) {
            activity.window.statusBarColor = color
            //                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            FlymeSetStatusBarLightMode(activity.window, true)
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = activity.window
            window.statusBarColor = color
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            /*activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);*/
        }
    }
}

/**
 * 设置状态栏图标为深色和魅族特定的文字风格
 * 可以用来判断是否为Flyme用户
 *
 * @param window 需要设置的窗口
 * @param dark   是否把状态栏文字及图标颜色设置为深色
 * @return boolean 成功执行返回true
 */
fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
    var result = false
    if (window != null) {
        try {
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            if (dark) {
                value = value or bit
            } else {
                value = value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
            result = true
        } catch (e: Exception) {

        }

    }
    return result
}

/**
 * 需要MIUIV6以上
 *
 * @param activity
 * @param dark     是否把状态栏文字及图标颜色设置为深色
 * @return boolean 成功执行返回true
 */
fun MIUISetStatusBarLightMode(activity: Activity, dark: Boolean): Boolean {
    var result = false
    val window = activity.window
    if (window != null) {
        val clazz = window.javaClass
        try {
            var darkModeFlag = 0
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
            }
            result = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                if (dark) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    //                        activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    val flag = window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    window.decorView.systemUiVisibility = flag
                }
            }
        } catch (e: Exception) {

        }

    }
    return result
}