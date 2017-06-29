package com.blackmirror.dongda.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.AY.AYSysObject;
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
    public Map<String, AYFragment> fragments;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindingSubFragments();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void bindingSubFragments();
}
