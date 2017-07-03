package com.blackmirror.dongda.facade.PhoneLoginFacade.LoginFacadeCommands;

import com.blackmirror.dongda.command.AYRemoteCommand;

/**
 * Created by alfredyang on 03/07/2017.
 */
public class AYUpdateProfileCommand extends AYRemoteCommand {

    final private String TAG = "AYUpdateProfileCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
        return "http://www.altlys.com:9000/v2/profile/update";
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
