package com.blackmirror.dongda.presenter


import com.blackmirror.dongda.kdomain.interactor.uploadWeChatImageImpl
import com.blackmirror.dongda.kdomain.interactor.weChatLoginImpl
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.ui.WeChatLoginContract
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.setAuthToken
import com.blackmirror.dongda.utils.setUserId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeChatLoginPresenter @Inject constructor(val view: WeChatLoginContract.View) : WeChatLoginContract.WeChatLoginPresenter {

    override fun weChatLogin(provide_uid: String, provide_token: String, provide_screen_name: String,
                             provide_name: String, provide_screen_photo: String) {
        weChatLoginImpl(provide_uid, provide_token, provide_screen_name, provide_name, provide_screen_photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        setUserId(it.user_id)
                        setAuthToken(it.auth_token)
                        view.weChatLoginSuccess(it)
                    } else {
                        view.onError(it)
                    }
                },{val bean = BaseDataBean()
                    bean.code = AppConstant.NET_UNKNOWN_ERROR
                    bean.message = it.message
                    view.onError(bean)})

    }

    override fun upLoadWeChatIcon(userIcon: String, imgUUID: String) {
        uploadWeChatImageImpl(userIcon, imgUUID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({if (it.isSuccess) {
                    view.onUpLoadWeChatIconSuccess(it)
                } else {
                    view.onError(it)
                }},{val bean = BaseDataBean()
                    bean.code = AppConstant.NET_UNKNOWN_ERROR
                    bean.message = it.message
                    view.onError(bean)})
    }

    override fun setView(view: WeChatLoginContract.View) {

    }

    override fun destroy() {

    }
}
