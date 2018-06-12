package com.blackmirror.dongda.ui.activity

import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean
import com.blackmirror.dongda.kdomain.model.LikePopDomainBean
import com.blackmirror.dongda.kdomain.model.LikePushDomainBean
import com.blackmirror.dongda.ui.BasePresenter

/**
 * Create By Ruge at 2018-05-11
 */
interface ListMoreContract {

    interface View {

        fun onGetServiceMoreDataSuccess(bean: CareMoreDomainBean)

        fun onLikePushSuccess(bean: LikePushDomainBean)

        fun onLikePopSuccess(bean: LikePopDomainBean)

        fun onGetDataError(bean: BaseDataBean)

    }

    interface Presenter : BasePresenter<View> {

        fun getServiceMoreData(skipCount: Int, takeCount: Int, serviceType: String)

        fun likePush(service_id: String)

        fun likePop(service_id: String)
    }
}
