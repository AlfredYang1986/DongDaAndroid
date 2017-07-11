package com.blackmirror.dongda.Tools;

import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import static android.content.ContentValues.TAG;

/**
 * Created by alfredyang on 10/7/17.
 */

public class AYApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
        Log.d(TAG, "onCreate: ImageLoader instance" );

    }
}
