package com.blackmirror.dongda.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alfredyang on 3/7/17.
 */

public class ServiceData extends ArrayList {
    private static ServiceData instance = null;

    public synchronized static ServiceData getDataInstance() {
        if (instance == null) {
            instance = new ServiceData();
        }
        return instance;
    }

    public ArrayList getServDataWithArgs() {
        ArrayList arr = new ArrayList();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> dic = new HashMap<String, Object>();
            dic.put("service_title", "但是是开发规划刘是的是道德高尚");
            dic.put("service_addr", "回家人而葵花加快地方第三方而为");
            dic.put("service_price", "89");
            arr.add(dic);
        }

        return arr;
    }
}
