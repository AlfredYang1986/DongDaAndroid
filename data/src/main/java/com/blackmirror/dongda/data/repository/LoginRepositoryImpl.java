package com.blackmirror.dongda.data.repository;

import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean;
import com.blackmirror.dongda.data.model.response.SendSmsResponseBean;
import com.blackmirror.dongda.data.model.response.WeChatLoginResponseBean;
import com.blackmirror.dongda.data.net.LoginApi;
import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.model.SendSmsBean;
import com.blackmirror.dongda.domain.model.WeChatLoginBean;
import com.blackmirror.dongda.domain.repository.LoginRepository;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Ruge on 2018-05-04 下午1:50
 */
public class LoginRepositoryImpl implements LoginRepository {

    @Override
    public Observable<SendSmsBean> sendSms(String phone) {
        return LoginApi.sendSms(phone)
                .map(new Function<SendSmsResponseBean, SendSmsBean>() {
                    @Override
                    public SendSmsBean apply(SendSmsResponseBean bean) throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public Observable<PhoneLoginBean> phoneLogin(String phone, String code, String reg_token) {
        return LoginApi.phoneLogin(phone, code, reg_token)
                .map(new Function<PhoneLoginResponseBean, PhoneLoginBean>() {
                    @Override
                    public PhoneLoginBean apply(PhoneLoginResponseBean bean) throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public Observable<WeChatLoginBean> weChatLogin(String provide_uid, String provide_token, String provide_screen_name,
                                                   String provide_name, String provide_screen_photo) {
        return LoginApi.wechatLogin(provide_uid, provide_token, provide_screen_name, provide_name, provide_screen_photo)
                .map(new Function<WeChatLoginResponseBean, WeChatLoginBean>() {
                    @Override
                    public WeChatLoginBean apply(WeChatLoginResponseBean bean) throws Exception {
                        return null;
                    }
                });
    }
}
