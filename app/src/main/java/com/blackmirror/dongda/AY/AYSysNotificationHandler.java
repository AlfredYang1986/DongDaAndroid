package com.blackmirror.dongda.AY;

import org.json.JSONObject;

/**
 * Created by alfredyang on 24/05/2017.
 */
public interface AYSysNotificationHandler extends AYSysObject {
    Boolean handleNotifications(String name, JSONObject args);
}
