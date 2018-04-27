package com.blackmirror.dongda.Tools;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfredyang on 10/7/17.
 */

public class AYApplication extends Application {

    public static Context appContext;
    private static Application me;
    public static boolean isLoginSuccess;


    public static List<AppCompatActivity> activityList;
    //    public static IWXAPI weChatApi;

    @Override
    public void onCreate() {
        super.onCreate();
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/
        me=this;
        appContext =this.getApplicationContext();
//        Stetho.initializeWithDefaults(this);
        initFresco();
//        initWeChat();
        initShareSDK();
        activityList= new ArrayList<>();
        initAMap();

    }

    /**
     * 初始化高德地图
     */
    public static void initAMap() {
    }

    public static void addActivity(AppCompatActivity activity){
        if (!activityList.contains(activity)){
            activityList.add(activity);
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

        ActivityManager activityManager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setCacheKeyFactory(MyCacheKeyFactory.getInstance())
                .setMainDiskCacheConfig(diskCacheConfig)
                .setDownsampleEnabled(true)
//                .setEncodedMemoryCacheParamsSupplier(new MyEncodeBitmapMemoryCacheParamsSupplier(activityManager))
                .setBitmapMemoryCacheParamsSupplier(new MyBitmapMemoryCacheParamsSupplier(activityManager))
                .setResizeAndRotateEnabledForNetwork(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setMemoryTrimmableRegistry(NoOpMemoryTrimmableRegistry.getInstance())
                .build();
        Fresco.initialize(this,config);

        NoOpMemoryTrimmableRegistry.getInstance().registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                double ratio = trimType.getSuggestedTrimRatio();
                LogUtils.d("trimType "+ratio);

                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == ratio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == ratio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == ratio) {
                    Fresco.getImagePipeline().clearMemoryCaches();
                    LogUtils.d("registerMemoryTrimmable "+ratio);
                }
            }
        });
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // Determine which lifecycle or system event was raised.
        switch (level) {

            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN://应用不可见 进入后台等
                if (isLoginSuccess) {
                    Fresco.getImagePipeline().clearMemoryCaches();
                }
                break;

            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                Fresco.getImagePipeline().clearMemoryCaches();


                break;

            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
//            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
//            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:


                break;

            default:
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
                break;
        }
        LogUtils.d("onTrimMemory level="+level);
        /*try {
            if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) { // 60
                ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
            }
        } catch (Exception e) {

        }*/
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtils.d("onLowMemory ");

        try {
            ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
        } catch (Exception e) {

        }
    }

    public static Context getAppContext(){
        return appContext;
    }

    public static Application getApplication(){
        return me;
    }

    public static void getImgTokenByRepeat(){

    }

}
