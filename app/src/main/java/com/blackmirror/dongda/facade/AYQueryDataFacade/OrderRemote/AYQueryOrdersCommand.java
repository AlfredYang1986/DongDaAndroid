package com.blackmirror.dongda.facade.AYQueryDataFacade.OrderRemote;

import com.blackmirror.dongda.command.AYRemoteCommand;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYQueryOrdersCommand extends AYRemoteCommand {

    final private String TAG = "AYQueryOrdersCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
        return kDONGDABASEURL + "v3/order/query";
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
