package com.blackmirror.dongda.data.model.request

class UploadImageRequestBean : BaseRequestBean() {
    var imgUUID: String? = null
    var userIcon: String? = null
    var userIconData: ByteArray? = null
}
