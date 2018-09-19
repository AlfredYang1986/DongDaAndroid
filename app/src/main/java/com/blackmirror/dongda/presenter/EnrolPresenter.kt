package com.blackmirror.dongda.presenter

import com.blackmirror.dongda.kdomain.interactor.enrolStudentImpl
import com.blackmirror.dongda.kdomain.interactor.getBrandAllLocationImpl
import com.blackmirror.dongda.kdomain.interactor.getLocAllServiceImpl
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.ui.activity.enrol.EnrolContract
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Create By Ruge at 2018-05-15
 */
class EnrolPresenter @Inject constructor(val view: EnrolContract.View?) : EnrolContract.Presenter {

    override fun getBrandAllLocation(brand_id: String) {
        getBrandAllLocationImpl(brand_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        view?.onGetBrandAllLocationSuccess(it)
                        LogUtils.d("apply onNext isSuccess")
                    } else {
                        LogUtils.d("apply onNext onError")
                        view?.onError(it)
                    }
                }, {
                    LogUtils.e(EnrolPresenter::class.java, it)

                    view?.onError(getErrorData(it))
                })
    }

    override fun getLocAllService(json: String, locations: String) {
        getLocAllServiceImpl(json, locations)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        view?.onGetLocAllServiceSuccess(it)
                        LogUtils.d("apply onNext isSuccess")
                    } else {
                        LogUtils.d("apply onNext onError")
                        view?.onError(it)
                    }
                }, {
                    LogUtils.e(EnrolPresenter::class.java, it)

                    view?.onError(getErrorData(it))
                })
    }

    override fun enrol(json: String) {
        enrolStudentImpl(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        view?.onEnrolSuccess(it)
                        LogUtils.d("apply onNext isSuccess")
                    } else {
                        LogUtils.d("apply onNext onError")
                        view?.onError(it)
                    }
                }, {
                    LogUtils.e(EnrolPresenter::class.java, it)

                    view?.onError(getErrorData(it))
                })
    }

    private fun getErrorData(e: Throwable): BaseDataBean {
        val bean = BaseDataBean()
        bean.code = AppConstant.NET_UNKNOWN_ERROR
        bean.message = e.message
        return bean
    }


    override fun setView(view: EnrolContract.View) {

    }

    override fun destroy() {

    }
}
