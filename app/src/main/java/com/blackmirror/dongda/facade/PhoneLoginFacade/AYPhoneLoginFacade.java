package com.blackmirror.dongda.facade.PhoneLoginFacade;

import com.blackmirror.dongda.facade.AYFacade;
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
