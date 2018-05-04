package com.blackmirror.dongda.facade.userfacade.usercmd;

import com.blackmirror.dongda.command.AYRemoteCommand;
import com.blackmirror.dongda.utils.AppConstant;

/**
 * Created by xcx on 2018/4/16.
 */

public class AYQueryUserInfoCmd extends AYRemoteCommand {
    private final String TAG = "AYQueryUserInfoCmd";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
        return AppConstant.QUERY_USER_INFO_URL;
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
