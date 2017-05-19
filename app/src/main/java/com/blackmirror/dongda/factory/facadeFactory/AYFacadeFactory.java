package com.blackmirror.dongda.factory.facadeFactory;

import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.blackmirror.dongda.factory.common.AYFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alfredyang on 12/05/2017.
 */
public class AYFacadeFactory extends AYFactory {

    final private String TAG = "AYFacadeFactory";

    private AYFacade facade_instance = null;

    @Override
    public AYSysObject creation() {
        if (facade_instance == null) {
            facade_instance = (AYFacade) super.creation();
            initCmdsLst(facade_instance);
        }
        return facade_instance;
    }

    @Override
    public void postCreation(AYSysObject f) {
        super.postCreation(f);
        AYFacade facade = (AYFacade)f;
        initCmdsLst(facade);
    }

    protected void initCmdsLst(AYFacade facade) {
        List<String> lst = this.getSubInstanceName("command");
        Map<String, AYCommand> result = new HashMap<>();
        for (String iter : lst) {
            AYCommand tmp = (AYCommand) AYFactoryManager.getInstance(null)
                                .queryInstance("command", iter);
            result.put(iter, tmp);
        }
        facade.cmds = result;
    }

    @Override
    public String getClassTag() {
        return TAG;
    }
}
