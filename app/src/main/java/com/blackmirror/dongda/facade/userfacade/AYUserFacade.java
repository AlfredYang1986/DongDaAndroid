package com.blackmirror.dongda.facade.userfacade;

import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;

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

    /**
     * 获取用户信息回调
     * @param args
     */
    public void AYQueryUserInfoCmdSuccess(JSONObject args){
        broadcastingNotification("AYQueryUserInfoCmdSuccess", args);
    }


    public void AYQueryUserInfoCmdFailed(JSONObject args) {
        broadcastingNotification("AYQueryUserInfoCmdFailed", args);
    }


    /**
     * 修改用户信息回调
     * @param args
     * @return
     */
    public void AYUpdateProfileCommandSuccess(JSONObject args) {
        /**
         * 修改用户信息到数据库
         */
        AYDaoUserProfile p = new AYDaoUserProfile(args,true);
        p.setIs_current(1);

        AYFacade f = (AYFacade) AYFactoryManager.getInstance(null).queryInstance("facade", "DongdaCommanFacade");
        AYCommand cmd = f.cmds.get("LoginSuccess");
        cmd.excute(p);
        broadcastingNotification("AYUpdateProfileCommandSuccess", args);
    }

    public void AYUpdateProfileCommandFailed(JSONObject args) {
        broadcastingNotification("AYUpdateProfileCommandFailed", args);
    }

    /**
     * 上传文件回调
     * @param args
     */
    public void AYUploadFileBySDKCommandSuccess(JSONObject args){
        broadcastingNotification("AYUploadFileBySDKCommandSuccess", args);
    }

    public void AYUploadFileBySDKCommandFailed(JSONObject args) {
        broadcastingNotification("AYUploadFileBySDKCommandFailed", args);
    }

}
