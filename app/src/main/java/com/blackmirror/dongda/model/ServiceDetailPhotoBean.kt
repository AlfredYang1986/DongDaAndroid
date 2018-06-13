package com.blackmirror.dongda.model

/**
 * Created by Ruge on 2018-04-19 下午3:54
 */
class ServiceDetailPhotoBean {
    var tag: String? = null
    var image: String? = null

    constructor(tag: String, image: String) {
        this.tag = tag
        this.image = image
    }

    constructor() {

    }
}
