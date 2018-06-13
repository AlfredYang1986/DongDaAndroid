package com.blackmirror.dongda.data.model.request

/**
 * Created by Ruge on 2018-05-04 下午2:21
 */
class PhoneLoginRequestBean : BaseRequestBean() {
    var phone_number: String? = null
    var code: String? = null
    var reg_token: String? = null
}
