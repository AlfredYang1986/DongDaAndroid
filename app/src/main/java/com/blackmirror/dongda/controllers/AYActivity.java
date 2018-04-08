package com.blackmirror.dongda.controllers;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
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

        bindingFragments();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterCallback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerCallback();
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

    public boolean sendMessageToFragment (String frag, String methodName, JSONObject args) {


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
        isViewValid = false;
        super.onDestroy();
    }

    protected abstract void bindingFragments();
}
