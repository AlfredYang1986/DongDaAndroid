package com.blackmirror.dongda.command;

import android.util.Log;

/**
 * Created by alfredyang on 18/05/2017.
 */
public class AYCommandTest extends AYCommand {

    final private String TAG = "AYCommandTest";

    @Override
    public <Args, Result> Result excute(Args[] _) {
        Log.i(TAG, "command test");
        return null;
    }

    @Override
    public String getClassTag() {
        return TAG;
    }
}
