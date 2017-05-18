package com.blackmirror.dongda.controllers;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.AYFacade;

import java.util.Map;

/**
 * Created by alfredyang on 17/05/2017.
 */
public abstract class AYActivity extends AppCompatActivity implements AYSysObject {

    public Map<String, AYCommand> cmds;
    public Map<String, AYFacade> facades;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        /**
         * 从工厂中，查询Activitiy中需要的facade，Command，Fregment 并依次创建
         * 将关联在Activitiy的commands 和 facade 关联起来
         */
//        AYFactory<AYActivity> fac = (AYFactory<AYActivity>) AYFactoryManager.getInstance().queryFactoryByName(getClassTag());
//        fac.preCreation(getMetaType(), getMetaName());
////        fac.createInstance();
//        fac.postCreation(this);
    }

    @Override
    public String getClassTag() {
        return "AYActivity";
    }
}
