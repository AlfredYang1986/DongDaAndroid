package com.blackmirror.dongda.factory;

import com.blackmirror.dongda.AY.AYSysObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alfredyang on 12/05/2017.
 */
public class AYFactoryManager {
    private static AYFactoryManager instance;

    public AYFactoryManager getInstance() {
        //TODO: 修改成线程安全的
        if (instance == null) {
            instance = new AYFactoryManager();
        }
        return instance;
    }

    private Map<String, AYSysObject> manager = new HashMap<>();

    public AYSysObject queryCmdOrFacadeByName(String name) {

    }
}
