package com.blackmirror.dongda.data.model.request

class WeChatLoginRequestBean : BaseRequestBean() {
    var provide_uid: String? = null
    var provide_token: String? = null
    var provide_screen_name: String? = null
    var provide_name: String? = null
    var provide_screen_photo: String? = null
}
