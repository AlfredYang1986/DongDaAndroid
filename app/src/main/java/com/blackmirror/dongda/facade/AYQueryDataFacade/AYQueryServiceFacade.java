package com.blackmirror.dongda.facade.AYQueryDataFacade;

import com.blackmirror.dongda.facade.AYFacade;

import org.json.JSONObject;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYQueryServiceFacade extends AYFacade {

    private final String TAG = "AYQueryServiceFacade";

    @Override
    public String getClassTag() {
        return TAG;
    }

    public Boolean AYSearchServiceCommandSuccess(JSONObject args) {
        broadcastingNotification("AYSearchServiceCommandSuccess", args);
        return true;
    }

    public Boolean AYSearchServiceCommandFailed(JSONObject args) {
        broadcastingNotification("AYSearchServiceCommandFailed", args);
        return true;
    }
}
