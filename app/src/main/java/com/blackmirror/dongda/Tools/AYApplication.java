package com.blackmirror.dongda.Tools;

import android.app.Application;
import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by alfredyang on 10/7/17.
 */

public class AYApplication extends Application {

    public static Context appConext;
    private static Application me;
    public static IWXAPI weChatApi;

    @Override
    public void onCreate() {
        super.onCreate();
        me=this;
        appConext=this.getApplicationContext();
        Stetho.initializeWithDefaults(this);
        initFresco();
        initWeChat();
    }

    /**
     * 初始化微信登录相关参数
     */
    private void initWeChat() {
        //第二个参数是指你应用在微信开放平台上的AppID
        weChatApi = WXAPIFactory.createWXAPI(this, AppConstant.WECHAT_APP_ID, false);
        // 将该app注册到微信
        weChatApi.registerApp(AppConstant.WECHAT_APP_ID);
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
