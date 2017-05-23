package com.blackmirror.dongda.command.LoginCommand;

import android.util.Log;
import com.blackmirror.dongda.command.AYRemoteCommand;

/**
 * Created by alfredyang on 23/05/2017.
 */
public class AYLoginWithPhoneCommand extends AYRemoteCommand {

    final private String TAG = "AYLoginWithPhoneCommand";

    @Override
    public <Args, Result> Result excute(Args[] _) {
        Log.i(TAG, "AYLoginWithPhoneCommand");
        return null;
    }

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected String getUrl() {
        return "altlys.com:9000/";
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
