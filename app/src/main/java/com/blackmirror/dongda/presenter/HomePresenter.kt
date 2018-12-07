package com.blackmirror.dongda.presenter


import com.blackmirror.dongda.kdomain.interactor.likePopImpl
import com.blackmirror.dongda.kdomain.interactor.likePushImpl
import com.blackmirror.dongda.kdomain.interactor.searchServiceImpl
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.ui.activity.homeActivity.HomeContract
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Create By Ruge at 2018-05-11
 */
class HomePresenter @Inject constructor(val view: HomeContract.HomeView?) : HomeContract.HomeBasePresenter {

    override fun getHomePageData() {
        searchServiceImpl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        view?.onGetHomePageData(it)
                    } else {
                        view?.onGetHomeDataError(it)
                    }
                },{
                    view?.onGetHomeDataError(getErrorData(it))
                })
    }

    override fun likePush(service_id: String) {
        likePushImpl(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({if (it.isSuccess) {
                    view?.onLikePushSuccess(it)
                } else {
                    view?.onGetHomeDataError(it)
                }},{
                    logE(message = HomePresenter::class.java.simpleName, exception = it)
                    view?.onGetHomeDataError(getErrorData(it))
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
                        view?.onGetHomeDataError(it)
                    }
                },{e->
                    logE(message = HomePresenter::class.java.simpleName, exception = e)
                    view?.onGetHomeDataError(getErrorData(e))
                })
    }

    private fun getErrorData(e: Throwable): BaseDataBean {
        val bean = BaseDataBean()
        bean.code = AppConstant.NET_UNKNOWN_ERROR
        bean.message = e.message
        return bean
    }

    override fun setView(view: HomeContract.HomeView) {

    }

    override fun destroy() {

    }
}
