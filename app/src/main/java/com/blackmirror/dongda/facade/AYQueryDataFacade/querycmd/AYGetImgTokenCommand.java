package com.blackmirror.dongda.facade.AYQueryDataFacade.querycmd;

import com.blackmirror.dongda.command.AYRemoteCommand;
import com.blackmirror.dongda.utils.AppConstant;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYGetImgTokenCommand extends AYRemoteCommand {

    final private String TAG = "AYGetImgTokenCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
//        return kDONGDABASEURL +  "v3.1/kidnap/search";
        return AppConstant.OSS_INFO_URL;
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
