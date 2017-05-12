package com.blackmirror.dongda.facade;

import com.blackmirror.dongda.command.AYCommand;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYFacadeActivityRef {
    public abstract Boolean HandleAYFacadeNotify(AYCommand cmd);
}
