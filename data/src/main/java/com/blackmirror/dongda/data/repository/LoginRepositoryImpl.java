package com.blackmirror.dongda.data.repository;

import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.db.UserInfoDbBean;
import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean;
import com.blackmirror.dongda.data.model.response.SendSmsResponseBean;
import com.blackmirror.dongda.data.model.response.UpLoadImgResponseBean;
import com.blackmirror.dongda.data.model.response.WeChatLoginResponseBean;
import com.blackmirror.dongda.data.net.LoginApi;
import com.blackmirror.dongda.data.net.UpLoadWeChatIconApi;
import com.blackmirror.dongda.data.net.UpdateUserInfoApi;
import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.model.SendSmsBean;
import com.blackmirror.dongda.domain.model.UpLoadWeChatIconDomainBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.domain.model.WeChatLoginBean;
import com.blackmirror.dongda.domain.repository.LoginRepository;
import com.blackmirror.dongda.utils.AYPrefUtils;

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
                    public SendSmsBean apply(SendSmsResponseBean responseBean) throws Exception {
                        SendSmsBean bean = new SendSmsBean();
                        if (responseBean != null && "ok".equals(responseBean.status)) {
                            bean.isSuccess = true;
                            if (responseBean.result != null && responseBean.result.reg != null) {
                                bean.phone = responseBean.result.reg.getPhone();
                                //                                bean.code=responseBean.result.reg.code;
                                bean.reg_token = responseBean.result.reg.getReg_token();
                                bean.is_reg = responseBean.result.reg.getIs_reg();
                            }
                        } else {
                            if (responseBean != null && responseBean.error != null) {
                                bean.code = responseBean.error.code;
                                bean.message = responseBean.error.message;
                            }
                        }
                        return bean;
                    }
                });
    }

    @Override
    public Observable<PhoneLoginBean> phoneLogin(String phone, String code, String reg_token) {
        return LoginApi.phoneLogin(phone, code, reg_token)
                .map(new Function<PhoneLoginResponseBean, PhoneLoginBean>() {
                    @Override
                    public PhoneLoginBean apply(PhoneLoginResponseBean responseBean) throws Exception {
                        PhoneLoginBean bean = new PhoneLoginBean();
                        if (bean != null && "ok".equals(responseBean.status)) {
                            bean.isSuccess = true;
                            if (responseBean.result != null) {
                                bean.auth_token = responseBean.result.auth_token;
                            }
                            if (responseBean.result != null && responseBean.result.user != null) {
                                bean.screen_name = responseBean.result.user.screen_name;
                                bean.has_auth_phone = responseBean.result.user.has_auth_phone;
                                bean.current_device_type = responseBean.result.user.current_device_type;
                                bean.is_service_provider = responseBean.result.user.is_service_provider;
                                bean.user_id = responseBean.result.user.user_id;
                                bean.screen_photo = responseBean.result.user.screen_photo;
                                bean.current_device_id = responseBean.result.user.current_device_id;
                            }
                        } else {
                            if (bean != null && responseBean.error != null) {
                                bean.code = responseBean.error.code;
                                bean.message = responseBean.error.message;
                            }
                        }
                        //插入数据库
                        if (bean.isSuccess) {
                            AYPrefUtils.setUserId(bean.user_id);
                            AYPrefUtils.setAuthToken(bean.auth_token);

                            UserInfoDbBean dbBean = new UserInfoDbBean();
                            dbBean.is_current = 1;//目前没什么卵用
                            dbBean.screen_name = bean.screen_name;
                            dbBean.screen_photo = bean.screen_photo;
                            dbBean.user_id = bean.user_id;
                            dbBean.auth_token = bean.auth_token;
                            DbRepository.insertProfile(dbBean);
                        }
                        return bean;
                    }
                });
    }

    @Override
    public Observable<WeChatLoginBean> weChatLogin(String provide_uid, String provide_token, String provide_screen_name,
                                                   String provide_name, String provide_screen_photo) {
        return LoginApi.wechatLogin(provide_uid, provide_token, provide_screen_name, provide_name, provide_screen_photo)
                .map(new Function<WeChatLoginResponseBean, WeChatLoginBean>() {
                    @Override
                    public WeChatLoginBean apply(WeChatLoginResponseBean responseBean) throws Exception {
                        WeChatLoginBean bean = new WeChatLoginBean();
                        bean.isSuccess = "ok".equals(responseBean.status);

                        if ("ok".equals(responseBean.status)) {
                            bean.isSuccess = true;
                            if (responseBean.result != null) {
                                bean.auth_token = responseBean.result.auth_token;
                                if (responseBean.result.user != null) {
                                    bean.screen_name = responseBean.result.user.screen_name;
                                    bean.has_auth_phone = responseBean.result.user.has_auth_phone;
                                    bean.current_device_type = responseBean.result.user.current_device_type;
                                    bean.is_service_provider = responseBean.result.user.is_service_provider;
                                    bean.user_id = responseBean.result.user.user_id;
                                    bean.screen_photo = responseBean.result.user.screen_photo;
                                    bean.current_device_id = responseBean.result.user.current_device_id;
                                }
                            }

                        } else {
                            if (responseBean.error != null) {
                                bean.code = responseBean.error.code;
                                bean.message = responseBean.error.message;
                            }
                        }
                        //插入数据库
                        if (bean.isSuccess) {

                            AYPrefUtils.setUserId(bean.user_id);
                            AYPrefUtils.setAuthToken(bean.auth_token);

                            UserInfoDbBean dbBean = new UserInfoDbBean();
                            dbBean.is_current = 1;//目前没什么卵用
                            dbBean.screen_name = bean.screen_name;
                            dbBean.screen_photo = bean.screen_photo;
                            dbBean.user_id = bean.user_id;
                            dbBean.auth_token = bean.auth_token;
                            DbRepository.insertProfile(dbBean);
                        }

                        return bean;
                    }
                });
    }

    @Override
    public Observable<UpLoadWeChatIconDomainBean> upLoadWeChatIcon(String userIcon, final String imgUUID) {
        return UpLoadWeChatIconApi.uploadWeChatImage(userIcon, imgUUID)
                .flatMap(new Function<UpLoadImgResponseBean, Observable<UpLoadWeChatIconDomainBean>>() {
                    @Override
                    public Observable<UpLoadWeChatIconDomainBean> apply(UpLoadImgResponseBean bean) throws Exception {

                        if ("ok".equals(bean.status)) {
                            UpdateUserInfoDomainBean ub = new UpdateUserInfoDomainBean();
                            ub.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"profile\":{\"screen_photo\":\"" + imgUUID + "\"}}";

                            return UpdateUserInfoApi.updateUserInfo(ub).map(new Function<UpdateUserInfoBean, UpLoadWeChatIconDomainBean>() {
                                @Override
                                public UpLoadWeChatIconDomainBean apply(UpdateUserInfoBean bean) throws Exception {
                                    UpLoadWeChatIconDomainBean db = new UpLoadWeChatIconDomainBean();
                                    if (bean.isSuccess) {
                                        db.isSuccess = true;
                                        db.imgUUID = bean.screen_photo;
                                    } else {
                                        db.code = bean.code;
                                        db.message = bean.message;
                                    }
                                    return db;
                                }
                            });
                        } else {
                            UpLoadWeChatIconDomainBean db = new UpLoadWeChatIconDomainBean();
                            db.code = DataConstant.UPLOAD_WECHAT_ERROR;
                            db.message = "上传微信头像失败!";
                            return Observable.just(db);
                        }
                    }


                });
    }
}
