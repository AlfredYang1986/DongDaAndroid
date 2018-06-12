package com.blackmirror.dongda.ui.activity

import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoDomainBean
import com.blackmirror.dongda.kdomain.model.UserInfoDomainBean
import com.blackmirror.dongda.ui.BasePresenter

/**
 * Create By Ruge at 2018-05-11
 */
interface UserInfoContract {

    interface View {

        fun onQueryUserInfoSuccess(bean: UserInfoDomainBean)

        fun onGetDataError(bean: BaseDataBean)

    }

    interface Presenter : BasePresenter<View> {

        fun queryUserInfo()

        fun updateUserInfo(bean: UpdateUserInfoDomainBean)

    }
}
