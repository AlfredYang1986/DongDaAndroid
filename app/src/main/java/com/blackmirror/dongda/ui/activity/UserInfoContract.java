package com.blackmirror.dongda.ui.activity;

import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.UserInfoDomainBean;
import com.blackmirror.dongda.ui.BasePresenter;

/**
 * Create By Ruge at 2018-05-11
 */
public interface UserInfoContract {

    interface View{

        void onQueryUserInfoSuccess(UserInfoDomainBean bean);

        void onGetDataError(BaseDataBean bean);

    }
    interface presenter extends BasePresenter<View>{

        void queryUserInfo();

    }
}
