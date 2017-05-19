package com.blackmirror.dongda.facade;

import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYFacade implements AYSysObject {
    public Map<String, AYCommand> cmds;

    private ArrayList<AYFacadeActivityRef> handlers;

    public Boolean registerActivity(AYFacadeActivityRef ref) {
        if (!handlers.contains(ref))
            handlers.add(ref);

        return true;
    }
}
