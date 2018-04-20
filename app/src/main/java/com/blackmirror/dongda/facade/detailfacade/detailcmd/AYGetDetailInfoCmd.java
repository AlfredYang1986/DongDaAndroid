package com.blackmirror.dongda.facade.detailfacade.detailcmd;

import com.blackmirror.dongda.command.AYRemoteCommand;

/**
 * Created by xcx on 2018/4/16.
 */

public class AYGetDetailInfoCmd extends AYRemoteCommand {
    private final String TAG = "AYGetDetailInfoCmd";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
        return "http://192.168.100.174:9000/al/service/detail";
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