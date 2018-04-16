package com.blackmirror.dongda.facade.userfacade.usercmd;

import com.blackmirror.dongda.command.AYRemoteCommand;

/**
 * Created by xcx on 2018/4/16.
 */

public class AYEditUserInfoCmd extends AYRemoteCommand {
    private final String TAG = "AYEditUserInfoCmd";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
        return null;
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
