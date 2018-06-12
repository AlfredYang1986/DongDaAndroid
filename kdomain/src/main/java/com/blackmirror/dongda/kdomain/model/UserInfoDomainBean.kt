package com.blackmirror.dongda.kdomain.model

/**
 * Create By Ruge at 2018-05-15
 */
class UserInfoDomainBean : BaseDataBean() {
    var screen_name: String? = null
    var description: String? = null
    var has_auth_phone: Int = 0
    var owner_name: String? = null
    var is_service_provider: Int = 0//0 不是 1 是
    var user_id: String? = null
    var company: String? = null
    var screen_photo: String? = null
    var date: Long = 0
    var address: String? = null
    var contact_no: String? = null
    var social_id: String? = null
}
