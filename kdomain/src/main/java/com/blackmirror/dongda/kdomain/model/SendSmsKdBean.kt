package com.blackmirror.dongda.kdomain.model

/**
 * Create By Ruge at 2018-06-11
 */
class SendSmsKdBean :BaseDataBean(){
    var status: String? = null//ok 成功 其他失败
    var phone: String? = null
    var reg_token: String? = null
    var is_reg: Int = 0

    override fun toString(): String {
        return "SendSmsKdBean(status=$status, phone=$phone, reg_token=$reg_token, is_reg=$is_reg, isSuccess=$isSuccess, code=$code, message=$message)"
    }
}