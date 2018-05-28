package com.blackmirror.dongda.ui.activity;

import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.CareMoreDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.ui.BasePresenter;

/**
 * Create By Ruge at 2018-05-11
 */
public interface ListMoreContract {

    interface View{

        void onGetServiceMoreDataSuccess(CareMoreDomainBean bean);

        void onLikePushSuccess(LikePushDomainBean bean);

        void onLikePopSuccess(LikePopDomainBean bean);

        void onGetDataError(BaseDataBean bean);

    }
    interface presenter extends BasePresenter<View>{

        void getServiceMoreData(int skipCount, int takeCount, String serviceType);

        void likePush(String service_id);

        void likePop(String service_id);
    }
}
