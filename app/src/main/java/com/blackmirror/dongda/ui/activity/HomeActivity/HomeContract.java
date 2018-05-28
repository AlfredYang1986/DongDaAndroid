package com.blackmirror.dongda.ui.activity.HomeActivity;

import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.HomepageDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.ui.BasePresenter;

/**
 * Create By Ruge at 2018-05-11
 */
public interface HomeContract {
    interface HomeView {
        void onGetHomePageData(HomepageDomainBean bean);

        void onLikePushSuccess(LikePushDomainBean bean);

        void onLikePopSuccess(LikePopDomainBean bean);

        void onGetHomeDataError(BaseDataBean bean);

    }

    interface HomeBasePresenter extends BasePresenter<HomeView> {

        void getHomePageData();

        void likePush(String service_id);

        void likePop(String service_id);
    }
}
