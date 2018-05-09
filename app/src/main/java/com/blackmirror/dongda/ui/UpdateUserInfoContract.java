package com.blackmirror.dongda.ui;

import com.blackmirror.dongda.domain.model.UpdateUserInfoRequestBean;

/**
 * Created by xcx on 2018/5/9.
 */

public class UpdateUserInfoContract {
    public interface View{
        void onUpdateSuccess();
        void onUpdateError();
    }

    public interface UpdatePresenter extends BasePresenter<View>{
        void updateUserInfo(UpdateUserInfoRequestBean bean);
    }

}
