package com.blackmirror.dongda.facade.detailfacade.detailcmd;

import com.blackmirror.dongda.command.AYRemoteCommand;
import com.blackmirror.dongda.utils.AppConstant;

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
        return AppConstant.SERVICE_DETAIL_URL;
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
