package com.blackmirror.dongda.presenter

import com.blackmirror.dongda.kdomain.interactor.applyServiceImpl
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.ui.activity.apply.ApplyContract
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.logD
import com.blackmirror.dongda.utils.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Create By Ruge at 2018-05-15
 */
class ApplyPresenter @Inject constructor(val view: ApplyContract.View?) : ApplyContract.Presenter {

    override fun apply(brand_name: String, name: String, category: String, phone: String, city: String) {
        applyServiceImpl(brand_name, name, category, phone, city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    logD("apply onNext")
                    if (it.isSuccess) {
                        view?.onApplySuccess(it)
                    } else {
                        view?.onError(it)
                    }
                }, {
                    logE(message = ApplyPresenter::class.java.simpleName, exception = it)
                    view?.onError(getErrorData(it))
                })

    }

    private fun getErrorData(e: Throwable): BaseDataBean {
        val bean = BaseDataBean()
        bean.code = AppConstant.NET_UNKNOWN_ERROR
        bean.message = e.message
        return bean
    }

    override fun setView(view: ApplyContract.View) {

    }

    override fun destroy() {

    }
}
