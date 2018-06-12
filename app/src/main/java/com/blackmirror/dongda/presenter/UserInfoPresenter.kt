package com.blackmirror.dongda.presenter

import com.blackmirror.dongda.kdomain.interactor.queryUserInfoImpl
import com.blackmirror.dongda.kdomain.interactor.updateUserInfoImpl
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoDomainBean
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.ui.activity.UserInfoContract
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Create By Ruge at 2018-05-15
 */
class UserInfoPresenter @Inject constructor() : UserInfoContract.Presenter {

     var userView: UserInfoContract.View? = null

     var nameInputView: Contract.NameInputView? = null

    override fun queryUserInfo() {
        queryUserInfoImpl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        userView?.onQueryUserInfoSuccess(it)
                    } else {
                        userView?.onGetDataError(it)
                    }
                },{
                    LogUtils.e(UserInfoPresenter::class.java, it)
                    val bean = BaseDataBean()
                    bean.code = AppConstant.NET_UNKNOWN_ERROR
                    bean.message = it.message
                    userView?.onGetDataError(bean)
                })
    }




    override fun updateUserInfo(bean: UpdateUserInfoDomainBean) {
        updateUserInfoImpl(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        nameInputView?.onUpdateUserInfoSuccess(it)
                    } else {
                        nameInputView?.onError(it)
                    }
                },{
                    LogUtils.e(UserInfoPresenter::class.java, it)

                    val b = BaseDataBean()
                    b.code = AppConstant.NET_UNKNOWN_ERROR
                    b.message = it.message
                    nameInputView?.onError(bean)
                })

    }

    override fun setView(view: UserInfoContract.View?) {
    }

    override fun destroy() {

    }
}
