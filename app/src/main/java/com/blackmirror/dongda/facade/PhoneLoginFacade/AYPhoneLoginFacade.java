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
        return true;
    }

    Boolean AYLoginWithPhoneCommandFailed(JSONObject args) {
        return true;
    }

    Boolean AYSendSMSCodeCommandSuccess(JSONObject args) {
        return true;
    }

    Boolean AYSendSMSCodeCommandFailed(JSONObject args) {
        return true;
    }
}
