package com.blackmirror.dongda.facade.DongdaCommonFacade.LoginSuccessCommand;

import com.blackmirror.dongda.command.AYCommand;

/**
 * Created by alfredyang on 26/05/2017.
 */
public class AYQueryCurrentLoginUserCommand extends AYCommand {

    final private String TAG = "AYQueryCurrentLoginUserCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    public <Args, Result> Result excute(Args ... defaultArgs) {
        return null;
    }
}
