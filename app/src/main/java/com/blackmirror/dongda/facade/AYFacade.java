package com.blackmirror.dongda.facade;

import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYFacade implements AYSysNotificationHandler {
    public Map<String, AYCommand> cmds;

    private ArrayList<AYSysNotificationHandler> handlers = new ArrayList<>();

    public Boolean registerActivity(AYSysNotificationHandler ref) {
        Boolean result = false;
        if (!handlers.contains(ref)) {
            handlers.add(ref);
            result = true;
        }

        return result;
    }

    public Boolean unRegisterActivity(AYSysNotificationHandler ref) {
        Boolean result = false;
        if (handlers.contains(ref)) {
            handlers.remove(ref);
            result = true;
        }

        return result;
    }
    
    public void broadcastingNotification(String cmd_name, JSONObject args) {
        for (AYSysNotificationHandler a : handlers) {
//            a.facadeCallback(cmd_name, args);
            AYSysHelperFunc.getInstance().handleNotifications(cmd_name, args, a);
        }
    }

    @Override
    public Boolean handleNotifications(String name, JSONObject args) {
        return AYSysHelperFunc.getInstance().handleNotifications(name, args, this);
    }

}
