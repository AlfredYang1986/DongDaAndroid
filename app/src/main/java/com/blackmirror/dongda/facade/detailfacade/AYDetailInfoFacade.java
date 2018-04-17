package com.blackmirror.dongda.facade.detailfacade;

import com.blackmirror.dongda.facade.AYFacade;

import org.json.JSONObject;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYDetailInfoFacade extends AYFacade {

    private final String TAG = "AYDetailInfoFacade";

    @Override
    public String getClassTag() {
        return TAG;
    }


    public void AYGetDetailInfoCmdSuccess(JSONObject args){
        broadcastingNotification("AYGetDetailInfoCmdSuccess", args);
    }


    public void AYGetDetailInfoCmdFailed(JSONObject args) {
        broadcastingNotification("AYGetDetailInfoCmdFailed", args);
    }


}
