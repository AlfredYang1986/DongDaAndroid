package com.blackmirror.dongda.Tools;

import android.app.ActivityManager;
import android.os.Build;

import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;

/**
 * Created by Ruge on 2018-04-24 下午4:23
 */
public class MyBitmapMemoryCacheParamsSupplier implements Supplier<MemoryCacheParams> {

    private ActivityManager activityManager;
    private static final int MAX_CACHE_ENTRIES = 20;
    private static final int MAX_CACHE_ASHM_ENTRIES = 56;
    private static final int MAX_CACHE_EVICTION_SIZE = 5;
    private static final int MAX_CACHE_EVICTION_ENTRIES = 5;

    public MyBitmapMemoryCacheParamsSupplier(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }


    @Override
    public MemoryCacheParams get() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new MemoryCacheParams(
                    getMaxCacheSize(),// 可用最大内存数，以字节为单位
                    10,// 内存中允许的最多图片数量
                    1*ByteConstants.MB,// 内存中准备清理但是尚未删除的总图片所可用的最大内存数，以字节为单位
                    Integer.MAX_VALUE,//内存中准备清除的图片最大数量
                    500*ByteConstants.KB);// 内存中单图片的最大大小
        } else {
            return new MemoryCacheParams(
                    getMaxCacheSize(),
                    MAX_CACHE_ASHM_ENTRIES,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE);
        }
    }

    private int getMaxCacheSize() {
        final int maxMemory = Math.min(activityManager.getMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);

        if (maxMemory < 32 * ByteConstants.MB) {
            return 4 * ByteConstants.MB;
        } else if (maxMemory < 64 * ByteConstants.MB) {
            return 6 * ByteConstants.MB;
        } else {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD) {
                return 8 * ByteConstants.MB;
            } else {
                return maxMemory / 4;
            }
        }
    }
}
