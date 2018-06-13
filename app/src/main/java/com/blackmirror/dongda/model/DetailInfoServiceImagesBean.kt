package com.blackmirror.dongda.model

import com.blackmirror.dongda.utils.StringUtils

/**
 * Created by xcx on 2018/5/20.
 */

class DetailInfoServiceImagesBean : Comparable<DetailInfoServiceImagesBean> {
    /**
     * tag : 8
     * image : 597ec6a9-79c6-4a6f-806f-f5a58f9629f5
     */

    var tag: String? = null
    var image: String? = null

    override fun compareTo(o: DetailInfoServiceImagesBean): Int {
        if (!StringUtils.isNumber(this.tag) || !StringUtils.isNumber(o.tag)) {
            return -1
        }
        return if (Integer.parseInt(this.tag) >= Integer.parseInt(o.tag)) {
            1
        } else {
            -1
        }
    }
}
