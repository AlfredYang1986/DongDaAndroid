package com.blackmirror.dongda.controllers;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.command.AYNotification;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.blackmirror.dongda.factory.common.AYFactory;

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

    protected <Arg> void facadeCallback(String short_cmd_name, Arg ... args) {
        AYCommand cmd = this.cmds.get(short_cmd_name);
        if (cmd != null) {
            if (cmd instanceof AYNotification)
                ((AYNotification) cmd).setContext(this);

            cmd.excute(args);
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
