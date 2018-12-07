package com.blackmirror.dongda.presenter

import com.blackmirror.dongda.kdomain.interactor.getDetailInfoImpl
import com.blackmirror.dongda.kdomain.interactor.likePopImpl
import com.blackmirror.dongda.kdomain.interactor.likePushImpl
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
class DetailInfoPresenter @Inject constructor(val detailInfoView: Contract.DetailInfoView?) : Contract.DetailInfoPresenter {

    override fun getDetailInfo(service_id: String) {
        getDetailInfoImpl(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        detailInfoView?.onGetDetailInfoSuccess(it)
                    } else {
                        detailInfoView?.onGetDataError(it)
                    }
                }, {
                    logE(message = DetailInfoPresenter::class.java.simpleName, exception = it)

                    detailInfoView?.onGetDataError(getErrorData(it))
                })
    }

    override fun likePush(service_id: String) {
        likePushImpl(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        detailInfoView?.onLikePushSuccess(it)
                    } else {
                        detailInfoView?.onGetDataError(it)
                    }
                }, {
                    logE(message = DetailInfoPresenter::class.java.simpleName, exception = it)
                    detailInfoView?.onGetDataError(getErrorData(it))
                })
    }

    override fun likePop(service_id: String) {
        likePopImpl(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        detailInfoView?.onLikePopSuccess(it)
                    } else {
                        detailInfoView?.onGetDataError(it)
                    }
                }, {
                    logE(message = DetailInfoPresenter::class.java.simpleName, exception = it)
                    detailInfoView?.onGetDataError(getErrorData(it))
                })
    }

    private fun getErrorData(e: Throwable): BaseDataBean {
        val bean = BaseDataBean()
        bean.code = AppConstant.NET_UNKNOWN_ERROR
        bean.message = e.message
        return bean
    }
}
