package com.blackmirror.dongda.factory.commandFactory;

import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.factory.common.AYFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by alfredyang on 17/05/2017.
 */
public class AYCmdFactory extends AYFactory {
    final private String TAG = "AYCommandFactory";

    @Override
    public String getClassTag() {
        return TAG;
    }
}
