package com.blackmirror.dongda.utils

import android.net.Uri
import com.facebook.cache.common.CacheKey
import com.facebook.cache.common.SimpleCacheKey
import com.facebook.imagepipeline.cache.BitmapMemoryCacheKey
import com.facebook.imagepipeline.cache.CacheKeyFactory
import com.facebook.imagepipeline.request.ImageRequest


/**
 * Created by Ruge on 2018-04-13 上午11:11
 */
class MyCacheKeyFactory protected constructor() : CacheKeyFactory {

    override fun getBitmapCacheKey(request: ImageRequest, callerContext: Any): CacheKey {

        return BitmapMemoryCacheKey(
                getCacheUrl(request.sourceUri.toString()),
                request.resizeOptions,
                request.rotationOptions,
                request.imageDecodeOptions, null, null,
                callerContext)
    }

    override fun getPostprocessedBitmapCacheKey(request: ImageRequest, callerContext: Any): CacheKey {
        val postprocessor = request.postprocessor
        val postprocessorCacheKey: CacheKey?
        val postprocessorName: String?
        if (postprocessor != null) {
            postprocessorCacheKey = postprocessor.postprocessorCacheKey
            postprocessorName = postprocessor.javaClass.name
        } else {
            postprocessorCacheKey = null
            postprocessorName = null
        }
        return BitmapMemoryCacheKey(
                getCacheUrl(request.sourceUri.toString()),
                request.resizeOptions,
                request.rotationOptions,
                request.imageDecodeOptions,
                postprocessorCacheKey,
                postprocessorName,
                callerContext)
    }

    override fun getEncodedCacheKey(request: ImageRequest, callerContext: Any?): CacheKey {
        return getEncodedCacheKey(request, request.sourceUri, callerContext)
    }

    override fun getEncodedCacheKey(
            request: ImageRequest,
            sourceUri: Uri,
            callerContext: Any?): CacheKey {
        return SimpleCacheKey(getCacheUrl(request.sourceUri.toString()))
    }


    protected fun getCacheUrl(url: String): String {
        return if (url.contains("?")) {
            url.substring(0, url.indexOf("?") + 1)
        } else url

    }

    /**
     * @return a [Uri] that unambiguously indicates the source of the image.
     */
    protected fun getCacheKeySourceUri(sourceUri: Uri): Uri {
        return sourceUri
    }

    companion object {

         var instance: MyCacheKeyFactory=MyCacheKeyFactory()
    }
}
