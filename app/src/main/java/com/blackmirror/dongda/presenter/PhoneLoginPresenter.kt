package com.blackmirror.dongda.presenter

import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean
import com.blackmirror.dongda.kdomain.interactor.getSmsFromServer
import com.blackmirror.dongda.kdomain.interactor.phoneLoginImpl
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.ui.PhoneLoginContract
import com.blackmirror.dongda.utils.AppConstant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Create By Ruge at 2018-06-11
 */
class PhoneLoginPresenter @Inject constructor(val view: PhoneLoginContract.View) : PhoneLoginContract.PhoneLoginPresenter{
    override fun login(bean: PhoneLoginRequestBean) {
        phoneLoginImpl(bean.phone_number,bean.code,bean.reg_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        view.loginSuccess(it)
                    } else {
                        view.onError(it)
                    }
                },{val bean = BaseDataBean()
                    bean.code = AppConstant.NET_UNKNOWN_ERROR
                    bean.message = it.message
                    view.onError(bean)})

    }

    override fun sendSms(bean: SendSmsRequestBean) {
        getSmsFromServer(bean.phone_number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        view.sendSmsSuccess(it)
                    } else {
                        view.onError(it)
                    }
                },{
                    val bean = BaseDataBean()
                    bean.code = AppConstant.NET_UNKNOWN_ERROR
                    bean.message = it.message
                    view.onError(bean)
                })
    }

    override fun setView(view: PhoneLoginContract.View?) {

    }

    override fun destroy() {

    }

}