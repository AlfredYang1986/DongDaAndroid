package com.blackmirror.dongda.ui


import com.blackmirror.dongda.domain.model.BaseDataBean
import com.blackmirror.dongda.domain.model.UpLoadWeChatIconDomainBean
import com.blackmirror.dongda.domain.model.WeChatLoginBean

class WeChatLoginContract {

    interface View {
        fun weChatLoginSuccess(bean: WeChatLoginBean)

        fun onUpLoadWeChatIconSuccess(bean: UpLoadWeChatIconDomainBean)

        fun onError(bean: BaseDataBean)

    }

    interface WeChatLoginPresenter : BasePresenter<View> {
        fun weChatLogin(provide_uid: String, provide_token: String, provide_screen_name: String,
                        provide_name: String, provide_screen_photo: String)

        fun upLoadWeChatIcon(userIcon: String, imgUUID: String)
    }
}
