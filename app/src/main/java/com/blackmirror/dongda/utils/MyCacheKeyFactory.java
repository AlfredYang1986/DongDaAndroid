package com.blackmirror.dongda.utils;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheKey;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;



/**
 * Created by Ruge on 2018-04-13 上午11:11
 */
public class MyCacheKeyFactory implements CacheKeyFactory {

    private static MyCacheKeyFactory sInstance = null;

    protected MyCacheKeyFactory() {
    }

    public static synchronized MyCacheKeyFactory getInstance() {
        if (sInstance == null) {
            sInstance = new MyCacheKeyFactory();
        }
        return sInstance;
    }

    @Override
    public CacheKey getBitmapCacheKey(ImageRequest request, Object callerContext) {

        return new BitmapMemoryCacheKey(
                getCacheUrl(request.getSourceUri().toString()),
                request.getResizeOptions(),
                request.getRotationOptions(),
                request.getImageDecodeOptions(),
                null,
                null,
                callerContext);
    }

    @Override
    public CacheKey getPostprocessedBitmapCacheKey(ImageRequest request, Object callerContext) {
        final Postprocessor postprocessor = request.getPostprocessor();
        final CacheKey postprocessorCacheKey;
        final String postprocessorName;
        if (postprocessor != null) {
            postprocessorCacheKey = postprocessor.getPostprocessorCacheKey();
            postprocessorName = postprocessor.getClass().getName();
        } else {
            postprocessorCacheKey = null;
            postprocessorName = null;
        }
        return new BitmapMemoryCacheKey(
                getCacheUrl(request.getSourceUri().toString()),
                request.getResizeOptions(),
                request.getRotationOptions(),
                request.getImageDecodeOptions(),
                postprocessorCacheKey,
                postprocessorName,
                callerContext);
    }

    @Override
    public CacheKey getEncodedCacheKey(ImageRequest request, @Nullable Object callerContext) {
        return getEncodedCacheKey(request, request.getSourceUri(), callerContext);
    }

    @Override
    public CacheKey getEncodedCacheKey(
            ImageRequest request,
            Uri sourceUri,
            @Nullable Object callerContext) {
        return new SimpleCacheKey(getCacheUrl(request.getSourceUri().toString()));
    }


    protected String getCacheUrl(String url){
        if (url.contains("?")){
            return url.substring(0,url.indexOf("?")+1);
        }
        return url;

    }

    /**
     * @return a {@link Uri} that unambiguously indicates the source of the image.
     */
    protected Uri getCacheKeySourceUri(Uri sourceUri) {
        return sourceUri;
    }
}
