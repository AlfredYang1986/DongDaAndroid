package com.blackmirror.dongda.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.command.AYNotification;
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
public abstract class AYActivity extends AppCompatActivity implements AYSysObject {

    public Map<String, AYCommand> cmds;
    public Map<String, AYFacade> facades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 从工厂中，查询Activitiy中需要的facade，Command，Fregment 并依次创建
         * 将关联在Activitiy的commands 和 facade 关联起来
         */
        AYFactory fac = AYFactoryManager.getInstance(this).queryFactoryInstance("controller", getClassTag());
        fac.postCreation(this);
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
        return "AYActivity";
    }

    public void facadeCallback(String short_cmd_name, JSONObject args) {
//        AYCommand cmd = this.cmds.get(short_cmd_name);
//        if (cmd != null) {
//            if (cmd instanceof AYNotification)
//                ((AYNotification) cmd).setContext(this);
//
//            cmd.excute(args);
//        }

        try {
            Method method = this.getClass().getMethod(short_cmd_name, JSONObject.class);
            method.invoke(this, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
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
}
