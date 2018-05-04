package com.blackmirror.dongda.facade.PhoneLoginFacade.LoginFacadeCommands;

import com.blackmirror.dongda.command.AYRemoteCommand;
import com.blackmirror.dongda.utils.AppConstant;

/**
 * Created by alfredyang on 23/05/2017.
 */
public class AYLoginWithPhoneCommand extends AYRemoteCommand {

    final private String TAG = "AYLoginWithPhoneCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
        return AppConstant.AUTH_SMS_CODE_URL;
    }

    @Override
    protected String getSuccessCallBackName() {
        return TAG + "Success";
    }

    @Override
    protected String getFailedCallBackName() {
        return TAG + "Failed";
    }
}
