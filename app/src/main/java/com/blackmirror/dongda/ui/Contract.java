package com.blackmirror.dongda.ui;

import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.DetailInfoDomainBean;
import com.blackmirror.dongda.domain.model.LikeDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.domain.model.NearServiceDomainBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;

/**
 * Create By Ruge at 2018-05-17
 */
public interface Contract {
    interface NameInputView {

        void onUpdateUserInfoSuccess(UpdateUserInfoBean bean);

        void onError(BaseDataBean bean);
    }

    interface MyLikeView {

        void onGetLikeDataSuccess(LikeDomainBean bean);

        void onLikePushSuccess(LikePushDomainBean bean);

        void onLikePopSuccess(LikePopDomainBean bean);

        void onGetDataError(BaseDataBean bean);
    }

    interface NearServiceView {

        void onGetNearServiceSuccess(NearServiceDomainBean bean);

        void onGetDataError(BaseDataBean bean);

    }

    interface DetailInfoView {

        void onGetDetailInfoSuccess(DetailInfoDomainBean bean);

        void onLikePushSuccess(LikePushDomainBean bean);

        void onLikePopSuccess(LikePopDomainBean bean);

        void onGetDataError(BaseDataBean bean);

    }

    interface LikePresenter{

        void getLikeData();

        void likePush(String service_id);

        void likePop(String service_id);
    }

    interface NearServicePresenter{

        void getNearService(double latitude, double longitude);
    }

    interface DetailInfoPresenter{

        void getDetailInfo(String service_id);

        void likePush(String service_id);

        void likePop(String service_id);
    }

}
