package com.blackmirror.dongda.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfredyang on 10/7/17.
 */

public class AYApplication extends Application {

    public static AYApplication me;
    public static Context appContext;
    public static List<AppCompatActivity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        me = this;
        appContext = getApplicationContext();
        activityList= new ArrayList<>();
    }

    public static void addActivity(AppCompatActivity activity){
        if (!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    public static void removeActivity(AppCompatActivity activity){
        if (activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    public static void finishActivity(AppCompatActivity activity){
        if (activityList.contains(activity)){
            activityList.remove(activity);
            activity.finish();
        }
    }

    public static void finishAllActivity(){
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i)!=null) {
                activityList.get(i).finish();
            }
        }
        activityList.clear();
    }

    public static Context getAppContext(){
        return appContext;
    }

    public static Application getApplication(){
        return me;
    }
}
