package com.blackmirror.dongda.ui;


import com.blackmirror.dongda.data.model.request.WeChatLoginRequestBean;
import com.blackmirror.dongda.domain.model.WeChatLoginBean;

public class WeChatLoginContract {

    public interface View{
        void weChatLoginSuccess(WeChatLoginBean bean);

        void weChatLoginError(WeChatLoginBean bean);

    }

    public interface WeChatLoginPresenter extends BasePresenter<View> {
        void weChatLogin(WeChatLoginRequestBean bean);
    }
}
