package com.blackmirror.dongda.command;

import com.blackmirror.dongda.AY.AYSysNotifier;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYCommand extends AYSysNotifier {
    public abstract <Args, Result> Result excute(Args ... defaultArgs);

    public String kDONGDABASEURL = "http://www.altlys.com:9000/";

    protected String cmd_name;
    public String getCmdName() {
        return cmd_name;
    }
    public void setCmdName(String n) {
        this.cmd_name = n;
    }
}
