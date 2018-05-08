package com.blackmirror.dongda.domain.repository;

import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.model.SendSmsBean;
import com.blackmirror.dongda.domain.model.WeChatLoginBean;

import io.reactivex.Observable;

public interface LoginRepository extends Repository {

    Observable<SendSmsBean> sendSms(String phone);

    Observable<PhoneLoginBean> phoneLogin(String phone, String code, String reg_token);

    Observable<WeChatLoginBean> weChatLogin(String provide_uid, String provide_token, String provide_screen_name,
                                            String provide_name, String provide_screen_photo);

}
