package com.blackmirror.dongda.facade;

import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;

import java.util.ArrayList;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYFacade implements AYSysObject {
    private ArrayList<AYCommand> cmds;
    private ArrayList<AYCommand> notifications;

    private ArrayList<AYFacadeActivityRef> handlers;

    public ArrayList<AYCommand> getCmds() {
        return cmds;
    }

    public ArrayList<AYCommand> getNotifications() {
        return notifications;
    }

    public Boolean registerActivity(AYFacadeActivityRef ref) {
        if (!handlers.contains(ref))
            handlers.add(ref);

        return true;
    }
}
