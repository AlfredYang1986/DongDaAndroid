package com.blackmirror.dongda.Tools;

import android.app.Application;
import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;

/**
 * Created by alfredyang on 10/7/17.
 */

public class AYApplication extends Application {

    private static Context appConext;
    private static Application me;

    @Override
    public void onCreate() {
        super.onCreate();
        me=this;
        appConext=this.getApplicationContext();
        Stetho.initializeWithDefaults(this);
        init();
    }

    /**
     * 初始化Fresco等控件
     */
    private void init() {
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
