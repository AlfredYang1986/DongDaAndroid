package com.blackmirror.dongda.Tools;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限适配相关
 * Created by Ruge on 2018-04-08 上午11:37
 */
public class PermissionUtils {
    public static List<String> checkPermissionWithNoGranted(AppCompatActivity activity, List<String> permissionList) {
        List<String> list = new ArrayList<>();
        if (activity == null || permissionList == null) {
            return list;
        }
        for (int i = 0; i < permissionList.size(); i++) {
            if (ContextCompat.checkSelfPermission(activity, permissionList.get(i)) != PackageManager.PERMISSION_GRANTED) {
                list.add(permissionList.get(i));
            }

        }
        return list;
    }

    public static List<String> checkPermissionWithNoGranted(AppCompatActivity activity, String[] permissionList) {
        List<String> list = new ArrayList<>();
        if (activity == null || permissionList == null) {
            return list;
        }
        for (int i = 0; i < permissionList.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permissionList[i]) != PackageManager.PERMISSION_GRANTED) {
                list.add(permissionList[i]);
            }

        }
        return list;
    }

    public static void requestMulitPermissions(AppCompatActivity activity, String[] list) {
        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        if (activity == null || list == null || list.length == 0) {
            return;
        }
        List<String> noGranted = checkPermissionWithNoGranted(activity, list);
        if (noGranted.size() != 0){
            ActivityCompat.requestPermissions(activity, list, AppConstant.PERMISSION_REQUEST);
        }
    }


}
