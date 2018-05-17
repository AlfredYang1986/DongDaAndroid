package com.blackmirror.dongda.ui;

import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;

/**
 * Create By Ruge at 2018-05-17
 */
public interface Contract {
     interface NameInputView{

         void onUpdateUserInfo(UpdateUserInfoBean bean);

         void onError(BaseDataBean bean);
    }
}
