package com.blackmirror.dongda.facade.AYQueryDataFacade.querycmd;

import com.blackmirror.dongda.command.AYRemoteCommand;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYSubjectMoreCommand extends AYRemoteCommand {

    final private String TAG = "AYSubjectMoreCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
//        return kDONGDABASEURL +  "v3.1/kidnap/search";
        return "http://192.168.100.174:9000/al/service/search";
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
