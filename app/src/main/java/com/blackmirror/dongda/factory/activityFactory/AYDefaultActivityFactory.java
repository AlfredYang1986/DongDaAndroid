package com.blackmirror.dongda.factory.activityFactory;

import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.blackmirror.dongda.factory.common.AYFactory;

/**
 * Created by alfredyang on 17/05/2017.
 */
public class AYDefaultActivityFactory extends AYFactory {

    final private String TAG = "default AYActivity Factory";

    @Override
    public void preCreation(String t) {

    }

    @Override
    public void postCreation(AYSysObject _) {
        /**
         * 从XML文件中得到关联的Command以及Facade
         */
//        AYFactoryManager.getInstance().queryFactoryByName("controller", controller.getMetaName());

    }

    @Override
    public AYSysObject creation() {
        // do nothing
        return null;
    }

    @Override
    public String getClassTag() {
        return TAG;
    }
}
