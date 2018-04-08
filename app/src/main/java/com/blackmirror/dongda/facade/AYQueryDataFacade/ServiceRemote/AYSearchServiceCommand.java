package com.blackmirror.dongda.facade.AYQueryDataFacade.ServiceRemote;

import com.blackmirror.dongda.command.AYRemoteCommand;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYSearchServiceCommand extends AYRemoteCommand {

    final private String TAG = "AYSearchServiceCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
        return kDONGDABASEURL +  "v3.1/kidnap/search";
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
