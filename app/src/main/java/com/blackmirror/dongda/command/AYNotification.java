package com.blackmirror.dongda.command;

import com.blackmirror.dongda.controllers.AYActivity;

/**
 * Created by alfredyang on 19/05/2017.
 */
public abstract class AYNotification extends AYCommand {
    protected AYActivity context;
    public void setContext(AYActivity c) {
        context = c;
    }
    public AYActivity getContext() {
        return context;
    }
}
