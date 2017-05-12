package com.blackmirror.dongda.command;

import com.blackmirror.dongda.AY.AYSysObject;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYCommand<Args, Result> implements AYSysObject {
    private String cmd_name;
    public abstract Result excute(Args ... _);

    public String getCmdName() {
        return cmd_name;
    }
}
