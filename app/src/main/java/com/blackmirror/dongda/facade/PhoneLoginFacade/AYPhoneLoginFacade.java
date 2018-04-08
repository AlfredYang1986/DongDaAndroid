package com.blackmirror.dongda.facade.PhoneLoginFacade;

import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.AYSQLiteProxy;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;
import org.json.JSONException;
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
        try {
            AYDaoUserProfile p = new AYDaoUserProfile(args.getJSONObject("result"));
            p.setIs_current(1);

            AYFacade f = (AYFacade) AYFactoryManager.getInstance(null).queryInstance("facade", "DongdaCommanFacade");
            AYCommand cmd = f.cmds.get("LoginSuccess");
            cmd.excute(p);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public Boolean AYSendSMSCodeCommandFailed(JSONObject args) {
        broadcastingNotification("AYSendSMSCodeCommandFailed", args);
        return true;
    }

    public Boolean AYUpdateProfileCommandSuccess(JSONObject args) {
        broadcastingNotification("AYUpdateProfileCommandSuccess", args);
        return true;
    }

    public Boolean AYUpdateProfileCommandFailed(JSONObject args) {
        broadcastingNotification("AYUpdateProfileCommandFailed", args);
        return true;
    }
}
