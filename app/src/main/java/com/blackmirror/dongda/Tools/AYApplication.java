package com.blackmirror.dongda.Tools;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;
import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfredyang on 10/7/17.
 */

public class AYApplication extends Application {

    public static Context appConext;
    private static Application me;

    public static List<AppCompatActivity> activityList;
    //    public static IWXAPI weChatApi;

    @Override
    public void onCreate() {
        super.onCreate();
        me=this;
        appConext=this.getApplicationContext();
        Stetho.initializeWithDefaults(this);
        initFresco();
//        initWeChat();
        initShareSDK();
        activityList= new ArrayList<>();
    }

    public static void addActivity(AppCompatActivity activity){
        if (!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    public static void finishAllActivity(){
        for (int i = 0; i < activityList.size(); i++) {
            activityList.get(i).finish();
        }
        activityList.clear();
    }



    private void initShareSDK() {
        MobSDK.init(this);
    }

    /**
     * 初始化微信登录相关参数
     */
    private void initWeChat() {
        /*//第二个参数是指你应用在微信开放平台上的AppID
        weChatApi = WXAPIFactory.createWXAPI(this, AppConstant.WECHAT_APP_ID, false);
        // 将该app注册到微信
        weChatApi.registerApp(AppConstant.WECHAT_APP_ID);*/
    }


    /**
     * 初始化Fresco相关参数
     */
    private void initFresco() {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(this.getExternalCacheDir())
                .setBaseDirectoryName("/image")
                .build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setCacheKeyFactory(MyCacheKeyFactory.getInstance())
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this,config);
    }

    public static Context getAppConext(){
        return appConext;
    }

    public static Application getApplication(){
        return me;
    }

}
