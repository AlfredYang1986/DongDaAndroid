package com.blackmirror.dongda.facade.DongdaCommonFacade.LoginSuccessCommand;

import com.blackmirror.dongda.command.AYCommand;

/**
 * Created by alfredyang on 26/05/2017.
 */
public class AYLogoutSuccessCommand extends AYCommand {

    final private String TAG = "AYLogoutSuccessCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    public <Args, Result> Result excute(Args ... defaultArgs) {
        return null;
    }
}
