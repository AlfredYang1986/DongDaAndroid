package com.blackmirror.dongda.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Created by alfredyang on 4/7/17.
 */

public class AYTools {

    private static AYTools instance = null;

    public synchronized static AYTools getInstance() {
        if (instance == null) {
            instance = new AYTools();
        }
        return instance;
    }

    public JSONObject getJSONFromMap(Map map) {
        Iterator iter = map.entrySet().iterator();
        JSONObject holder = new JSONObject();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();
            String key = (String) pairs.getKey();
            Map m = (Map) pairs.getValue();
            JSONObject data = new JSONObject();
            try {
                Iterator iter2 = m.entrySet().iterator();
                while (iter2.hasNext()) {
                    Map.Entry pairs2 = (Map.Entry) iter2.next();
                    data.put((String) pairs2.getKey(), (String) pairs2.getValue());
                }
                holder.put(key, data);
            } catch (JSONException e) {
                Log.e("Transforming", "There was an error packaging JSON", e);
            }
        }
        return holder;
    }

    public String getTimeStrHours() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.CHINA);
        String timeString = sdf.format(new Date().getTime());
        return timeString;
    }
}
