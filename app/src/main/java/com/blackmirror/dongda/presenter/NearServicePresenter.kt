package com.blackmirror.dongda.presenter

import com.blackmirror.dongda.kdomain.interactor.getNearServiceImpl
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Create By Ruge at 2018-05-18
 */
class NearServicePresenter @Inject constructor(val nearServiceView: Contract.NearServiceView?) : Contract.NearServicePresenter {

    override fun getNearService(latitude: Double, longitude: Double) {
        getNearServiceImpl(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        nearServiceView?.onGetNearServiceSuccess(it)
                    } else {
                        nearServiceView?.onGetDataError(it)
                    }
                }, {
                    logE(message = NearServicePresenter::class.java.simpleName, exception = it)

                    nearServiceView?.onGetDataError(getErrorData(it))
                })
    }

    private fun getErrorData(e: Throwable): BaseDataBean {
        val bean = BaseDataBean()
        bean.code = AppConstant.NET_UNKNOWN_ERROR
        bean.message = e.message
        return bean
    }
}
