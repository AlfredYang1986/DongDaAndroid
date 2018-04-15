package com.blackmirror.dongda.Tools;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

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

    public static void requestMulitPermissions(AppCompatActivity activity, List<String> list) {
        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        if (activity == null || list == null || list.size() == 0) {
            return;
        }

        String[] arr=new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i]=list.get(i);
        }

        ActivityCompat.requestPermissions(activity, arr, AppConstant.PERMISSION_REQUEST);


        /*List<String> noGranted = checkPermissionWithNoGranted(activity, list);
        if (noGranted.size() != 0){
            ActivityCompat.requestPermissions(activity, list, AppConstant.PERMISSION_REQUEST);
        }*/
    }

    public static void requestPermission(AppCompatActivity activity, String permission) {
        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        if (activity == null || TextUtils.isEmpty(permission)) {
            return;
        }


        /**
         * shouldShowRequestPermissionRationale()。如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
         * 注：如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 Don't ask again 选项，
         * 此方法将返回 false。如果设备规范禁止应用具有该权限，此方法也会返回 false。
         */
        if (ContextCompat.checkSelfPermission(activity,permission)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, AppConstant.PERMISSION_REQUEST);

            } else {
                //提示用户打开设置授权

            }
        }else {

        }




    }


}
