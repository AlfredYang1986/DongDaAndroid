package com.blackmirror.dongda.ui

import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.PhoneLoginBean
import com.blackmirror.dongda.kdomain.model.SendSmsKdBean

/**
 * Create By Ruge at 2018-06-11
 */
class PhoneLoginContract {
    interface View {
        fun loginSuccess(bean: PhoneLoginBean)

        fun sendSmsSuccess(bean: SendSmsKdBean)

        fun onError(bean: BaseDataBean)
    }

    interface PhoneLoginPresenter : BasePresenter<View> {
        fun login(bean: PhoneLoginRequestBean)

        fun sendSms(bean: SendSmsRequestBean)
    }
}