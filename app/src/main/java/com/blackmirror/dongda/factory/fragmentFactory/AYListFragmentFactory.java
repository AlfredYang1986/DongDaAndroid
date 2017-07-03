package com.blackmirror.dongda.factory.fragmentFactory;

import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.blackmirror.dongda.factory.common.AYFactory;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.AYListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alfredyang on 3/7/17.
 */

public class AYListFragmentFactory extends AYFactory {

    final private String TAG = "AYListFragmentFactory";

    @Override
    public void postCreation(AYSysObject f) {
        super.postCreation(f);
        AYListFragment fragment = (AYListFragment) f;
        initCmdsLst(fragment);
        initFacadeLst(fragment);
//        initSubFragmentLst(fragment);
    }

    protected void initCmdsLst(AYListFragment fragment) {
        List<String> lst = this.getSubInstanceName("command");
        Map<String, AYCommand> result = new HashMap<>();
        for (String iter : lst) {
            AYCommand tmp = (AYCommand) AYFactoryManager.getInstance(null)
                    .queryInstance("command", iter);

            tmp.setTarget(fragment);
            result.put(iter, tmp);
        }
        fragment.cmds = result;
    }

    protected void initFacadeLst(AYListFragment fragment) {
        List<String> lst = this.getSubInstanceName("facade");
        Map<String, AYFacade> result = new HashMap<>();
        for (String iter : lst) {
            AYFacade tmp = (AYFacade) AYFactoryManager.getInstance(null)
                    .queryInstance("facade", iter);
            result.put(iter, tmp);
        }
        fragment.facades = result;
    }

//    protected void initSubFragmentLst(AYListFragment fragment) {
//        List<String> lst = this.getSubInstanceName("fragment");
//        Map<String, AYFragment> result = new HashMap<>();
//        for (String iter : lst) {
//            AYFragment tmp = (AYFragment) AYFactoryManager.getInstance(null)
//                    .queryInstance("fragment", iter);
//
//            result.put(iter, tmp);
//        }
//        fragment.fragments = result;
//    }

    @Override
    public String getClassTag() {
        return TAG;
    }
}
