package com.blackmirror.dongda.presenter

import com.blackmirror.dongda.kdomain.interactor.getServiceMoreDataImpl
import com.blackmirror.dongda.kdomain.interactor.likePopImpl
import com.blackmirror.dongda.kdomain.interactor.likePushImpl
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.ui.activity.ListMoreContract
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Create By Ruge at 2018-05-11
 */
class GetMoreDataPresenter @Inject constructor(val view: ListMoreContract.View?) : ListMoreContract.Presenter {

    override fun getServiceMoreData(skipCount: Int, takeCount: Int, serviceType: String) {
        getServiceMoreDataImpl(skipCount, takeCount, serviceType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        view?.onGetServiceMoreDataSuccess(it)
                    } else {
                        view?.onGetDataError(it)
                    }
                }, {
                    logE(message = GetMoreDataPresenter::class.java.simpleName, exception = it)

                    view?.onGetDataError(getErrorData(it))
                })
    }

    override fun likePush(service_id: String) {
        likePushImpl(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        view?.onLikePushSuccess(it)
                    } else {
                        view?.onGetDataError(it)
                    }
                }, {
                    logE(message = GetMoreDataPresenter::class.java.simpleName, exception = it)
                    view?.onGetDataError(getErrorData(it))
                })
    }

    override fun likePop(service_id: String) {
        likePopImpl(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        view?.onLikePopSuccess(it)
                    } else {
                        view?.onGetDataError(it)
                    }
                }, {
                    logE(message = GetMoreDataPresenter::class.java.simpleName, exception = it)
                    view?.onGetDataError(getErrorData(it))
                })
    }

    private fun getErrorData(e: Throwable): BaseDataBean {
        val bean = BaseDataBean()
        bean.code = AppConstant.NET_UNKNOWN_ERROR
        bean.message = e.message
        return bean
    }

    override fun setView(view: ListMoreContract.View) {

    }

    override fun destroy() {

    }
}
