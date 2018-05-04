package com.blackmirror.dongda.facade.PhoneLoginFacade.LoginFacadeCommands;

import com.blackmirror.dongda.command.AYRemoteCommand;
import com.blackmirror.dongda.utils.AppConstant;

/**
 * Created by alfredyang on 25/05/2017.
 */
public class AYSendSMSCodeCommand extends AYRemoteCommand {

    final private String TAG = "AYSendSMSCodeCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
            return AppConstant.SEND_SMS_CODE_URL;
//        return "http://www.altlys.com:9000/v2/phone/code/send";
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
