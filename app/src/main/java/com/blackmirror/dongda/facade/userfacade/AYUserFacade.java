package com.blackmirror.dongda.facade.userfacade;

import com.blackmirror.dongda.facade.AYFacade;

import org.json.JSONObject;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYUserFacade extends AYFacade {

    private final String TAG = "AYUserFacade";

    @Override
    public String getClassTag() {
        return TAG;
    }


    public void AYEditUserInfoCmdSuccess(JSONObject args){
        broadcastingNotification("AYEditUserInfoCmdSuccess", args);
    }


    public void AYEditUserInfoCmdFailed(JSONObject args) {
        broadcastingNotification("AYEditUserInfoCmdFailed", args);
    }

    public void AYQueryUserInfoCmdSuccess(JSONObject args){
        broadcastingNotification("AYQueryUserInfoCmdSuccess", args);
    }


    public void AYQueryUserInfoCmdFailed(JSONObject args) {
        broadcastingNotification("AYQueryUserInfoCmdFailed", args);
    }

}
