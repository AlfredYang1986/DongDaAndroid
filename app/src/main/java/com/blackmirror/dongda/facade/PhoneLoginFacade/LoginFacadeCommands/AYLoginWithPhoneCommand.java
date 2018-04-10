package com.blackmirror.dongda.facade.PhoneLoginFacade.LoginFacadeCommands;

import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.command.AYRemoteCommand;

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
        if (LogUtils.DEBUG){
            return "http://192.168.100.174:9000/al/auth/code";
        }
        return "http://www.altlys.com:9000/v2/auth/code";
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
