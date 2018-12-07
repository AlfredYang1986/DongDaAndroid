package com.blackmirror.dongda.utils

import android.app.ActivityManager
import android.os.Build

import com.facebook.common.internal.Supplier
import com.facebook.common.util.ByteConstants
import com.facebook.imagepipeline.cache.MemoryCacheParams

/**
 * Created by Ruge on 2018-04-24 下午4:23
 */
class MyEncodeBitmapMemoryCacheParamsSupplier(private val activityManager: ActivityManager) : Supplier<MemoryCacheParams> {

    private val MAX_CACHE_ENTRIES = Integer.MAX_VALUE
    private val MAX_EVICTION_QUEUE_ENTRIES = MAX_CACHE_ENTRIES

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

        return MemoryCacheParams(
                maxCacheSize / 8, // 可用最大内存数，以字节为单位
                3, // 内存中允许的最多图片数量
                maxCacheSize / 8, // 内存中准备清理但是尚未删除的总图片所可用的最大内存数，以字节为单位
                3, //内存中准备清除的图片最大数量
                100 * ByteConstants.KB)// 内存中单图片的最大大小

    }
}
