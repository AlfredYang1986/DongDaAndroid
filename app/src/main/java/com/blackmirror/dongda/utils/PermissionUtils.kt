package com.blackmirror.dongda.utils

import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import java.util.*

/**
 * 权限适配相关
 * Create By Ruge at 2018-12-07
 */

fun checkPermissionWithNoGranted(activity: AppCompatActivity?, permissionList: MutableList<String>?): MutableList<String> {
    val list = mutableListOf<String>()
    if (activity == null || permissionList == null) {
        return list
    }
    for (i in permissionList.indices) {
        if (ContextCompat.checkSelfPermission(activity, permissionList[i]) != PackageManager.PERMISSION_GRANTED) {
            list.add(permissionList[i])
        }

    }
    return list
}

fun checkPermissionWithNoGranted(activity: AppCompatActivity?, permissionList: Array<String>?): MutableList<String> {
    val list = ArrayList<String>()
    if (activity == null || permissionList == null) {
        return list
    }
    for (i in permissionList.indices) {
        if (ContextCompat.checkSelfPermission(activity, permissionList[i]) != PackageManager.PERMISSION_GRANTED) {
            list.add(permissionList[i])
        }

    }
    return list
}

fun checkPermissionWithNoGrantedForArray(activity: AppCompatActivity?, permissionList: Array<String>?): Array<String> {
    val list = ArrayList<String>()
    if (activity == null || permissionList == null) {
        return arrayOf()
    }
    for (i in permissionList.indices) {
        if (ContextCompat.checkSelfPermission(activity, permissionList[i]) != PackageManager.PERMISSION_GRANTED) {
            list.add(permissionList[i])
        }

    }
    val arr = Array(list.size){
        ""
    }
    for (i in list.indices) {
        arr[i] = list[i]
    }
    return arr
}

fun checkPermissionAndNeedGranted(activity: AppCompatActivity?, permissionList: Array<String>?): MutableList<String> {
    val list = ArrayList<String>()
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return list
    }
    if (activity == null || permissionList == null) {
        return list
    }
    for (i in permissionList.indices) {
        if (ContextCompat.checkSelfPermission(activity, permissionList[i]) != PackageManager.PERMISSION_GRANTED && ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionList[i])) {
            list.add(permissionList[i])
        }

    }
    return list
}

fun requestMulitPermissions(activity: AppCompatActivity?, list: MutableList<String>?) {
    //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
    // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
    // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return
    }
    if (activity == null || list == null || list.size == 0) {
        return
    }

    val arr = arrayOfNulls<String>(list.size)
    for (i in list.indices) {
        arr[i] = list[i]
    }

    ActivityCompat.requestPermissions(activity, arr, AppConstant.PERMISSION_REQUEST)


    /*List<String> noGranted = checkPermissionWithNoGranted(activity, list);
        if (noGranted.size() != 0){
            ActivityCompat.requestPermissions(activity, list, DataConstant.PERMISSION_REQUEST);
        }*/
}

fun requestPermission(activity: AppCompatActivity?, permission: String) {
    //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
    // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
    // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return
    }
    if (activity == null || TextUtils.isEmpty(permission)) {
        return
    }


    /**
     * shouldShowRequestPermissionRationale()。如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
     * 注：如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 Don't ask again 选项，
     * 此方法将返回 false。如果设备规范禁止应用具有该权限，此方法也会返回 false。
     */
    if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), AppConstant.PERMISSION_REQUEST)

        } else {
            //提示用户打开设置授权

        }
    } else {

    }


}
