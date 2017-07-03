package com.blackmirror.dongda.factory.activityFactory;

import android.util.Log;
import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.blackmirror.dongda.factory.common.AYFactory;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.AYListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alfredyang on 17/05/2017.
 */
public class AYDefaultActivityFactory extends AYFactory {
    final private String TAG = "default AYActivity Factory";

    @Override
    public void postCreation(AYSysObject f) {
        AYActivity controller = (AYActivity)f;
        initCmdsLst(controller);
        initFacadeLst(controller);
        initFragmentLst(controller);
    }

    @Override
    public AYSysObject creation() {
        Log.i(TAG, "should never been here");
        return null;
    }

    @Override
    public String getClassTag() {
        return TAG;
    }

    protected void initCmdsLst(AYActivity controller) {
        List<String> lst = this.getSubInstanceName("command");
        Map<String, AYCommand> result = new HashMap<>();
        for (String iter : lst) {
            AYCommand tmp = (AYCommand) AYFactoryManager.getInstance(controller.getApplicationContext())
                                            .queryInstance("command", iter);
            result.put(iter, tmp);
        }
        controller.cmds = result;
    }

    protected void initFacadeLst(AYActivity controller) {
        List<String> lst = this.getSubInstanceName("facade");
        Map<String, AYFacade> result = new HashMap<>();
        for (String iter : lst) {
            AYFacade tmp = (AYFacade) AYFactoryManager.getInstance(controller.getApplicationContext())
                                            .queryInstance("facade", iter);
            result.put(iter, tmp);
        }
        controller.facades = result;
    }

    protected void initFragmentLst(AYActivity controller) {
        Map<String, Object> result = new HashMap<>();

        List<String> lst = this.getSubInstanceName("fragment");
        for (String iter : lst) {
            AYFragment tmp = (AYFragment) AYFactoryManager.getInstance(controller.getApplicationContext())
                    .queryInstance("fragment", iter);
            result.put(iter, tmp);
        }

        List<String> listfrag = this.getSubInstanceName("listfragment");
        for (String iter : listfrag) {
            AYListFragment tmp = (AYListFragment) AYFactoryManager.getInstance(controller.getApplicationContext())
                    .queryInstance("listfragment", iter);
            result.put(iter, tmp);
        }

        controller.fragments = result;
    }
}
