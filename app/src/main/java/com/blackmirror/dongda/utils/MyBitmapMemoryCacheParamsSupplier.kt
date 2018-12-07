package com.blackmirror.dongda.utils

import android.app.ActivityManager
import android.os.Build

import com.facebook.common.internal.Supplier
import com.facebook.common.util.ByteConstants
import com.facebook.imagepipeline.cache.MemoryCacheParams

/**
 * Created by Ruge on 2018-04-24 下午4:23
 */
class MyBitmapMemoryCacheParamsSupplier(private val activityManager: ActivityManager) : Supplier<MemoryCacheParams> {

    private val MAX_CACHE_ENTRIES = 20
    private val MAX_CACHE_ASHM_ENTRIES = 56
    private val MAX_CACHE_EVICTION_SIZE = 5
    private val MAX_CACHE_EVICTION_ENTRIES = 5

    private val maxCacheSize: Int
        get() {
            val maxMemory = Math.min(activityManager.memoryClass * ByteConstants.MB, Integer.MAX_VALUE)

            return if (maxMemory < 32 * ByteConstants.MB) {
                4 * ByteConstants.MB
            } else if (maxMemory < 64 * ByteConstants.MB) {
                6 * ByteConstants.MB
            } else {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD) {
                    8 * ByteConstants.MB
                } else {
                    maxMemory / 4
                }
            }
        }


    override fun get(): MemoryCacheParams {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MemoryCacheParams(
                    maxCacheSize, // 可用最大内存数，以字节为单位
                    10, // 内存中允许的最多图片数量
                    1 * ByteConstants.MB, // 内存中准备清理但是尚未删除的总图片所可用的最大内存数，以字节为单位
                    Integer.MAX_VALUE, //内存中准备清除的图片最大数量
                    500 * ByteConstants.KB)// 内存中单图片的最大大小
        } else {
            MemoryCacheParams(
                    maxCacheSize,
                    MAX_CACHE_ASHM_ENTRIES,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE)
        }
    }

}
