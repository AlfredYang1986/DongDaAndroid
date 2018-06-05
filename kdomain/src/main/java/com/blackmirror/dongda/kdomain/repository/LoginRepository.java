package com.blackmirror.dongda.kdomain.repository;


import com.blackmirror.dongda.kdomain.model.PhoneLoginBean;
import com.blackmirror.dongda.kdomain.model.SendSmsBean;
import com.blackmirror.dongda.kdomain.model.UpLoadWeChatIconDomainBean;
import com.blackmirror.dongda.kdomain.model.WeChatLoginBean;

import io.reactivex.Observable;

public interface LoginRepository extends Repository {

    Observable<SendSmsBean> sendSms(String phone);

    Observable<PhoneLoginBean> phoneLogin(String phone, String code, String reg_token);

    Observable<WeChatLoginBean> weChatLogin(String provide_uid, String provide_token, String provide_screen_name,
                                            String provide_name, String provide_screen_photo);

    Observable<UpLoadWeChatIconDomainBean> upLoadWeChatIcon(String userIcon, String imgUUID);

}
