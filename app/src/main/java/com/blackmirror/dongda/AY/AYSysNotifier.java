package com.blackmirror.dongda.AY;

/**
 * Created by alfredyang on 24/05/2017.
 */
public abstract class AYSysNotifier implements AYSysObject {
    protected AYSysNotificationHandler target = null;

    public AYSysNotificationHandler getTarget() {
        return target;
    }

    public void setTarget(AYSysNotificationHandler t) {
        target = t;
    }
}
