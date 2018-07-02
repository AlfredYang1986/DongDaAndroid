package com.blackmirror.dongda.model

import com.blackmirror.dongda.utils.isNumber

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
        if (tag == null || o.tag == null || !tag!!.isNumber() || !o.tag!!.isNumber()) {
            -1
        }
        return if (tag!!.toInt() >= o.tag!!.toInt()) {
            1
        } else {
            -1
        }
    }
}
