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

    /**
     * 收藏相关
     * @param args
     */
    public void AYLikePushCommandSuccess(JSONObject args){
        broadcastingNotification("AYLikePushCommandSuccess", args);
    }

    public void AYLikePushCommandFailed(JSONObject args) {
        broadcastingNotification("AYLikePushCommandFailed", args);
    }

    /**
     * 取消收藏相关
     * @param args
     */
    public void AYLikePopCommandSuccess(JSONObject args){
        broadcastingNotification("AYLikePopCommandSuccess", args);
    }


    public void AYLikePopCommandFailed(JSONObject args) {
        broadcastingNotification("AYLikePopCommandFailed", args);
    }


}
