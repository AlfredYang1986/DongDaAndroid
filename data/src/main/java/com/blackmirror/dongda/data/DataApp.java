package com.blackmirror.dongda.data;

import android.app.Application;

/**
 * Created by Ruge on 2018-05-07 下午2:21
 */
public class DataApp extends Application {

    public static DataApp me;

    @Override
    public void onCreate() {
        super.onCreate();
        me = this;
    }
}
