package com.blackmirror.dongda.facade.AYQueryDataFacade;

import com.blackmirror.dongda.utils.LogUtils;
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

    /**
     * 获取图片token 用于生成url签名
     * @param args
     */
    public void AYGetImgTokenCommandSuccess(JSONObject args){
        broadcastingNotification("AYGetImgTokenCommandSuccess", args);
    }

    public void AYGetImgTokenCommandFailed(JSONObject args) {
        broadcastingNotification("AYGetImgTokenCommandFailed", args);
    }

    /**
     * 获取更多信息列表
     * @param args
     */
    public void AYSubjectMoreCommandSuccess(JSONObject args){
        broadcastingNotification("AYSubjectMoreCommandSuccess", args);
    }

    public void AYSubjectMoreCommandFailed(JSONObject args) {
        LogUtils.d("zhe li zoule ");
        broadcastingNotification("AYSubjectMoreCommandFailed", args);
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

    /**
     * 收藏列表
     * @param args
     */
    public void AYLikeQueryCommandSuccess(JSONObject args){
        broadcastingNotification("AYLikeQueryCommandSuccess", args);
    }


    public void AYLikeQueryCommandFailed(JSONObject args) {
        broadcastingNotification("AYLikeQueryCommandFailed", args);

    }
}
