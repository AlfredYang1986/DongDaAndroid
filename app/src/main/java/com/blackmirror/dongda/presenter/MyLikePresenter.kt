package com.blackmirror.dongda.presenter

import com.blackmirror.dongda.kdomain.interactor.getMyLikeDataImpl
import com.blackmirror.dongda.kdomain.interactor.likePopImpl
import com.blackmirror.dongda.kdomain.interactor.likePushImpl
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Create By Ruge at 2018-05-18
 */
class MyLikePresenter @Inject constructor(val likeView: Contract.MyLikeView?) : Contract.LikePresenter {

    override fun getLikeData() {
        getMyLikeDataImpl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        likeView?.onGetLikeDataSuccess(it)
                    } else {
                        likeView?.onGetDataError(it)
                    }
                }, {
                    LogUtils.e(MyLikePresenter::class.java, it)
                    likeView?.onGetDataError(getErrorData(it))
                })
    }

    override fun likePush(service_id: String) {
        likePushImpl(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        likeView?.onLikePushSuccess(it)
                    } else {
                        likeView?.onGetDataError(it)
                    }
                }, {
                    LogUtils.e(MyLikePresenter::class.java, it)
                    likeView?.onGetDataError(getErrorData(it))
                })
    }

    override fun likePop(service_id: String) {
        likePopImpl(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        likeView?.onLikePopSuccess(it)
                    } else {
                        likeView?.onGetDataError(it)
                    }
                }, {
                    LogUtils.e(MyLikePresenter::class.java, it)
                    likeView?.onGetDataError(getErrorData(it))
                })
    }

    private fun getErrorData(e: Throwable): BaseDataBean {
        val bean = BaseDataBean()
        bean.code = AppConstant.NET_UNKNOWN_ERROR
        bean.message = e.message
        return bean
    }
}
