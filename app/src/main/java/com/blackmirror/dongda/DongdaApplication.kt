package com.blackmirror.dongda

import android.app.ActivityManager
import android.content.ComponentCallbacks2
import android.content.Context
import android.graphics.Bitmap
import android.support.multidex.MultiDex
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.utils.MyBitmapMemoryCacheParamsSupplier
import com.blackmirror.dongda.utils.MyCacheKeyFactory
import com.blackmirror.dongda.utils.logD
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.memory.MemoryTrimType
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.mob.MobSDK

/**
 * Create By Ruge at 2018-12-06
 */
class DongdaApplication : AYApplication() {

    override fun onCreate() {
        super.onCreate()
        initFresco()
        initShareSDK()
        initAMap()
        initDagger()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initFresco() {
        val diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(this.externalCacheDir)
                .setBaseDirectoryName("/image")
                .build()

        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val config = ImagePipelineConfig.newBuilder(this)
                .setCacheKeyFactory(MyCacheKeyFactory.instance)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setDownsampleEnabled(true)
                //                .setEncodedMemoryCacheParamsSupplier(new MyEncodeBitmapMemoryCacheParamsSupplier(activityManager))
                .setBitmapMemoryCacheParamsSupplier(MyBitmapMemoryCacheParamsSupplier(activityManager))
                .setResizeAndRotateEnabledForNetwork(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setMemoryTrimmableRegistry(NoOpMemoryTrimmableRegistry.getInstance())
                .build()
        Fresco.initialize(this, config)

        NoOpMemoryTrimmableRegistry.getInstance().registerMemoryTrimmable { trimType ->
            val ratio = trimType.suggestedTrimRatio
            logD("trimType $ratio")

            if (MemoryTrimType.OnCloseToDalvikHeapLimit.suggestedTrimRatio == ratio
                    || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.suggestedTrimRatio == ratio
                    || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.suggestedTrimRatio == ratio) {
                Fresco.getImagePipeline().clearMemoryCaches()
                logD("registerMemoryTrimmable $ratio")
            }
        }
    }

    private fun initShareSDK() {
        MobSDK.init(this)
    }

    private fun initAMap() {

    }

    private fun initDagger() {

    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Fresco.getImagePipeline().clearMemoryCaches()
        }
        logD("onTrimMemory level=$level")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        logD("onLowMemory ")
        logD("xx")
        try {
            ImagePipelineFactory.getInstance().imagePipeline.clearMemoryCaches()
        } catch (e: Exception) {

        }

    }


}