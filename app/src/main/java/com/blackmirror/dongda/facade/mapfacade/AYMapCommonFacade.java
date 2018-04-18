package com.blackmirror.dongda.facade.mapfacade;

import com.blackmirror.dongda.facade.AYFacade;

import org.json.JSONObject;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYMapCommonFacade extends AYFacade {

    private final String TAG = "AYMapCommonFacade";

    @Override
    public String getClassTag() {
        return TAG;
    }

    public void AYGetNearServiceCmdSuccess(JSONObject args) {
        broadcastingNotification("AYGetNearServiceCmdSuccess", args);
    }

    public void AYGetNearServiceCmdFailed(JSONObject args) {
        broadcastingNotification("AYGetNearServiceCmdFailed", args);
    }

}
