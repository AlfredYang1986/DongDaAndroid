package com.blackmirror.dongda.facade.WeChatLoginFacade.WeChatLoginCmd;

import com.blackmirror.dongda.command.AYRemoteCommand;

/**
 * Created by Ruge on 2018-04-11 下午3:31
 */
public class AYWeChatLoginCmd extends AYRemoteCommand{

    private final String TAG = "AYWeChatLoginCmd";

    @Override
    protected String getUrl() {
        return "http://192.168.100.174:9000/al/auth/sns";
    }

    @Override
    protected String getSuccessCallBackName() {
        return TAG + "Success";
    }

    @Override
    protected String getFailedCallBackName() {
        return TAG + "Failed";
    }

    @Override
    public String getClassTag() {
        return TAG;
    }
}
