package com.blackmirror.dongda.data;

import com.blackmirror.dongda.command.AYRemoteCommand;
import com.blackmirror.dongda.utils.AppConstant;

/**
 * Created by Ruge on 2018-04-11 下午3:31
 */
public class AYWeChatLoginCmd2 extends AYRemoteCommand{

    private final String TAG = "AYWeChatLoginCmd2";

    @Override
    protected String getUrl() {
        return AppConstant.WECHAT_LOGIN_URL;
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
