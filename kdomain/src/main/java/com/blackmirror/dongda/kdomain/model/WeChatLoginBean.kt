package com.blackmirror.dongda.kdomain.model

/**
 * Created by Ruge on 2018-04-19 下午6:09
 */
class WeChatLoginBean : BaseDataBean() {

    var auth_token: String? = null
    var screen_name: String? = null
    var has_auth_phone: Int = 0
    var current_device_type: String? = null
    var is_service_provider: Int = 0
    var user_id: String? = null
    var screen_photo: String? = null
    var current_device_id: String? = null


    override fun toString(): String {
        return "PhoneLoginBean{" +
                "auth_token='" + auth_token + '\''.toString() +
                ", screen_name='" + screen_name + '\''.toString() +
                ", has_auth_phone=" + has_auth_phone +
                ", current_device_type='" + current_device_type + '\''.toString() +
                ", is_service_provider=" + is_service_provider +
                ", user_id='" + user_id + '\''.toString() +
                ", screen_photo='" + screen_photo + '\''.toString() +
                ", current_device_id='" + current_device_id + '\''.toString() +
                ", code=" + code +
                ", message='" + message + '\''.toString() +
                ", isSuccess=" + isSuccess +
                '}'.toString()
    }
}
