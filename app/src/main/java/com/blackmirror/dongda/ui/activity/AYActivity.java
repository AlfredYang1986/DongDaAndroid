package com.blackmirror.dongda.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.utils.OtherUtils;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.blackmirror.dongda.factory.common.AYFactory;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by alfredyang on 17/05/2017.
 */
public abstract class AYActivity extends AppCompatActivity implements AYSysNotificationHandler {

    private boolean isViewValid = false;
    public Map<String, AYCommand> cmds;
    public Map<String, AYFacade> facades;
    public Map<String, Object> fragments;
    protected FragmentManager mFragmentManage;
    protected ProgressDialog pb;
    private boolean isRegisterOnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 从工厂中，查询Activitiy中需要的facade，Command，Fregment 并依次创建
         * 将关联在Activitiy的commands 和 facade 关联起来
         */
        AYFactory fac = AYFactoryManager.getInstance(this).queryFactoryInstance("controller", getClassTag());
        fac.postCreation(this);

        mFragmentManage = getSupportFragmentManager();

        isViewValid = true;
        isRegisterOnCreate = true;

        bindingFragments();
        setStatusBarColor();
        registerCallback();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isRegisterOnCreate) {
            registerCallback();
        }
    }

    protected void setStatusBarColor() {
        OtherUtils.setStatusBarColor(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRegisterOnCreate = false;
//        unRegisterCallback();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public String getClassTag() {
        return this.getClass().getSimpleName();
    }

    protected void registerCallback() {
        for (AYFacade f : this.facades.values()) {
            f.registerActivity(this);
        }
    }

    protected void unRegisterCallback() {
        for (AYFacade f : this.facades.values()) {
            f.unRegisterActivity(this);
        }
    }

    @Override
    public Boolean handleNotifications(String name, JSONObject args) {
        return AYSysHelperFunc.getInstance().handleNotifications(name, args, this);
    }

    public boolean sendMessageToFragment(String frag, String methodName, JSONObject args) {


        Boolean result = true;
        try {
            Method method = (this.fragments.get(frag)).getClass().getMethod(methodName, JSONObject.class);
            method.invoke(this.fragments.get(frag), args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            result = false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            result = false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            result = false;
        }

        if (!result) {
            Log.i("method Invoke", "method invoke error");
        }

        return result;
    }

    protected boolean isViewValid() {
        return isViewValid;
    }

    @Override
    protected void onDestroy() {
        unRegisterCallback();
        closeProcessDialog();
        pb = null;
        isViewValid = false;
        super.onDestroy();
    }

    protected abstract void bindingFragments();

    protected void showProcessDialog() {
        showProcessDialog("提示", "正在处理中...");
    }

    protected void showProcessDialog(String message) {
        showProcessDialog("提示", message);
    }

    protected void showProcessDialog(String message, boolean cancelable) {
        showProcessDialog("提示", message, cancelable);
    }

    protected void showProcessDialog(String title, String message) {
        showProcessDialog(title, message, false);
    }

    protected void showProcessDialog(String title, String message,boolean cancelable) {
        if (!isViewValid()) {
            return;
        }
        if (pb == null) {
            pb = new ProgressDialog(this);
        }
        pb.setCanceledOnTouchOutside(cancelable);//设置在点击Dialog外是否取消Dialog进度条
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        pb.setCancelable(true);// 设置是否可以通过点击Back键取消
//        pb.setCanceledOnTouchOutside(false);//
        pb.setTitle(title);
        pb.setMessage(message);
        pb.onBackPressed();
        pb.show();
    }

    protected void closeProcessDialog() {
        if (pb != null && pb.isShowing()) {
            pb.dismiss();
            pb = null;
        }
    }
}
