package com.blackmirror.dongda.facade;

import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYFacade implements AYSysObject {
    public Map<String, AYCommand> cmds;

    private ArrayList<AYActivity> handlers = new ArrayList<>();

    public Boolean registerActivity(AYActivity ref) {
        Boolean result = false;
        if (!handlers.contains(ref)) {
            handlers.add(ref);
            result = true;
        }

        return result;
    }

    public Boolean unRegisterActivity(AYActivity ref) {
        Boolean result = false;
        if (handlers.contains(ref)) {
            handlers.remove(ref);
            result = true;
        }

        return result;
    }
    
    public void broadcastingNotification(String cmd_name, JSONObject args) {
        for (AYActivity a : handlers) {
            a.facadeCallback(cmd_name, args);
        }
    }
}
