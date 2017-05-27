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

    Boolean AYLoginWithPhoneCommandSuccess(JSONObject args) {

        /**
         * 将当前的登陆的用的存入数据库中
         */

        AYDaoUserProfile p = new AYDaoUserProfile();
        try {
            p.setUser_id(args.getJSONObject("result").getString("user_id"));
            p.setAuth_token(args.getJSONObject("result").getString("auth_token"));
            p.setScreen_name(args.getJSONObject("result").getString("screen_name"));
            p.setScreen_photo(args.getJSONObject("result").getString("screen_photo"));
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

    Boolean AYLoginWithPhoneCommandFailed(JSONObject args) {
        broadcastingNotification("AYLoginWithPhoneCommandFailed", args);
        return true;
    }

    Boolean AYSendSMSCodeCommandSuccess(JSONObject args) {
        broadcastingNotification("AYSendSMSCodeCommandSuccess", args);
        return true;
    }

    Boolean AYSendSMSCodeCommandFailed(JSONObject args) {
        broadcastingNotification("AYSendSMSCodeCommandFailed", args);
        return true;
    }
}
