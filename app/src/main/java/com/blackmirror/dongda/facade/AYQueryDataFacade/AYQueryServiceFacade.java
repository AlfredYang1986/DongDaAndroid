package com.blackmirror.dongda.facade.AYQueryDataFacade;

import com.blackmirror.dongda.facade.AYRemoteCommonFacade;

import org.json.JSONObject;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYQueryServiceFacade extends AYRemoteCommonFacade {

    private final String TAG = "AYQueryServiceFacade";

    @Override
    public String getClassTag() {
        return TAG;
    }

    Boolean AYSearchServiceCommandSuccess(JSONObject args) {
        broadcastingNotification("AYSearchServiceCommandSuccess", args);
        return true;
    }

    Boolean AYSearchServiceCommandFailed(JSONObject args) {
        broadcastingNotification("AYSearchServiceCommandFailed", args);
        return true;
    }
}
