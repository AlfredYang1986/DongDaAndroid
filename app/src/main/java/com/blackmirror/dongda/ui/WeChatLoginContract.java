package com.blackmirror.dongda.ui;


import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.UpLoadWeChatIconDomainBean;
import com.blackmirror.dongda.domain.model.WeChatLoginBean;

public class WeChatLoginContract {

    public interface View{
        void weChatLoginSuccess(WeChatLoginBean bean);

        void onUpLoadWeChatIconSuccess(UpLoadWeChatIconDomainBean bean);

        void onError(BaseDataBean bean);

    }

    public interface WeChatLoginPresenter extends BasePresenter<View> {
        void weChatLogin(String provide_uid, String provide_token, String provide_screen_name,
                         String provide_name, String provide_screen_photo);

        void upLoadWeChatIcon(String userIcon, String imgUUID);
    }
}
