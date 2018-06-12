package com.blackmirror.dongda.ui.activity.homeActivity

import com.blackmirror.dongda.kdomain.model.LikePopDomainBean
import com.blackmirror.dongda.kdomain.model.LikePushDomainBean
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.HomepageDomainBean
import com.blackmirror.dongda.ui.BasePresenter

/**
 * Create By Ruge at 2018-05-11
 */
interface HomeContract {
    interface HomeView {
        fun onGetHomePageData(bean: HomepageDomainBean)

        fun onLikePushSuccess(bean: LikePushDomainBean)

        fun onLikePopSuccess(bean: LikePopDomainBean)

        fun onGetHomeDataError(bean: BaseDataBean)

    }

    interface HomeBasePresenter : BasePresenter<HomeView> {

        fun getHomePageData()

        fun likePush(service_id: String)

        fun likePop(service_id: String)
    }
}
