package com.blackmirror.dongda

import com.blackmirror.dongda.data.model.request.SendSmsRequestBean
import com.blackmirror.dongda.domain.model.BaseDataBean
import com.blackmirror.dongda.domain.model.PhoneLoginBean
import com.blackmirror.dongda.kdomain.model.SendSmsKdBean
import com.blackmirror.dongda.presenter.PhoneLoginPresenter
import com.blackmirror.dongda.ui.PhoneLoginContract
import org.junit.Test

/**
 * Create By Ruge at 2018-06-11
 */
class Test {
    @Test
    fun testSms() {
        val p = PhoneLoginPresenter(object : PhoneLoginContract.View {
            override fun loginSuccess(bean: PhoneLoginBean) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun sendSmsSuccess(bean: SendSmsKdBean) {
                println("sendSmsSuccess $bean")
            }

            override fun onError(bean: BaseDataBean) {
                println("onError code=${bean.code} message=${bean.message}")
            }

        })
        val b = SendSmsRequestBean()
        b.phone_number = "17610279929"
        p.sendSms(b)
    }
}