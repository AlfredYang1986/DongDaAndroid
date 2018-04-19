package com.blackmirror.dongda.facade.PhoneLoginFacade;

import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;

import org.json.JSONObject;

/**
 * Created by alfredyang on 23/05/2017.
 */
public class AYPhoneLoginFacade extends AYFacade {
    private final String TAG = "AYPhoneLoginFacade";

    @Override
    public String getClassTag() {
        return TAG;
    }

    public Boolean AYLoginWithPhoneCommandSuccess(JSONObject args) {

        /**
         * 将当前的登陆的用的存入数据库中
         */

        AYDaoUserProfile p = new AYDaoUserProfile(args);
        p.setIs_current(1);

        AYFacade f = (AYFacade) AYFactoryManager.getInstance(null).queryInstance("facade", "DongdaCommanFacade");
        AYCommand cmd = f.cmds.get("LoginSuccess");
        cmd.excute(p);


        broadcastingNotification("AYLoginWithPhoneCommandSuccess", args);
        return true;
    }

    public Boolean AYLoginWithPhoneCommandFailed(JSONObject args) {
        broadcastingNotification("AYLoginWithPhoneCommandFailed", args);
        return true;
    }

    public Boolean AYSendSMSCodeCommandSuccess(JSONObject args) {
        broadcastingNotification("AYSendSMSCodeCommandSuccess", args);
        return true;
    }

    public Boolean AYWeChatLoginCmdSuccess(JSONObject args) {
        broadcastingNotification("AYWeChatLoginCmdSuccess", args);
        return true;
    }

    public Boolean AYWeChatLoginCmdFailed(JSONObject args) {
        broadcastingNotification("AYWeChatLoginCmdFailed", args);
        return true;
    }

    public Boolean AYSendSMSCodeCommandFailed(JSONObject args) {
        broadcastingNotification("AYSendSMSCodeCommandFailed", args);
        return true;
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
        AYDaoUserProfile p = new AYDaoUserProfile(args);
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
     * 上传文件回调
     * @param args
     */
    public void AYUploadFileBySDKCommandSuccess(JSONObject args){
        broadcastingNotification("AYUploadFileBySDKCommandSuccess", args);
    }

    public void AYUploadFileBySDKCommandFailed(JSONObject args) {
        broadcastingNotification("AYUploadFileBySDKCommandSuccess", args);
    }
}
