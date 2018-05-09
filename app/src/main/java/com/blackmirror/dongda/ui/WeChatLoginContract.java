package com.blackmirror.dongda.ui;


import com.blackmirror.dongda.domain.model.WeChatLoginBean;

public class WeChatLoginContract {

    public interface View{
        void weChatLoginSuccess(WeChatLoginBean bean);

        void weChatLoginError(WeChatLoginBean bean);

    }

    public interface WeChatLoginPresenter extends BasePresenter<View> {
        void weChatLogin(String provide_uid, String provide_token, String provide_screen_name,
                         String provide_name, String provide_screen_photo);
    }
}
