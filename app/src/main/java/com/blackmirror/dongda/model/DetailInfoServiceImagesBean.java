package com.blackmirror.dongda.model;

import android.support.annotation.NonNull;

import com.blackmirror.dongda.utils.StringUtils;

/**
 * Created by xcx on 2018/5/20.
 */

public class DetailInfoServiceImagesBean implements Comparable<DetailInfoServiceImagesBean>{
    /**
     * tag : 8
     * image : 597ec6a9-79c6-4a6f-806f-f5a58f9629f5
     */

    public String tag;
    public String image;

    @Override
    public int compareTo(@NonNull DetailInfoServiceImagesBean o) {
        if (!StringUtils.isNumber(this.tag) || !StringUtils.isNumber(o.tag)){
            return -1;
        }
        if (Integer.parseInt(this.tag)>=Integer.parseInt(o.tag)){
            return 1;
        }else {
            return -1;
        }
    }
}
