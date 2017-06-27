package com.blackmirror.dongda.fragment;

import android.support.v4.app.Fragment;
import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.AYFacade;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by alfredyang on 27/06/2017.
 */
public abstract class AYFragment extends Fragment implements AYSysNotificationHandler {

    public Map<String, AYCommand> cmds;
    public Map<String, AYFacade> facades;

    @Override
    public void onPause() {
        super.onPause();
        unRegisterCallback();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerCallback();
    }

    @Override
    public String getClassTag() {
        return "AYFragment";
    }

    protected void registerCallback() {
        if (this.facades == null) return;

        for (AYFacade f : this.facades.values()) {
            f.registerActivity(this);
        }
    }

    protected void unRegisterCallback() {
        if (this.facades == null) return;

        for (AYFacade f : this.facades.values()) {
            f.unRegisterActivity(this);
        }
    }

    @Override
    public Boolean handleNotifications(String name, JSONObject args) {
        return AYSysHelperFunc.getInstance().handleNotifications(name, args, this);
    }
}
