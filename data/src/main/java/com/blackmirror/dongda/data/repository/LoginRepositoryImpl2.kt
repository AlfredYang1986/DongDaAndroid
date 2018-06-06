package com.blackmirror.dongda.data.repository

import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.db.UserInfoDbBean
import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean
import com.blackmirror.dongda.data.model.request.UploadImageRequestBean
import com.blackmirror.dongda.data.model.request.WeChatLoginRequestBean
import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean
import com.blackmirror.dongda.data.model.response.WeChatLoginResponseBean
import com.blackmirror.dongda.data.net.execute
import com.blackmirror.dongda.data.net.sendSMS
import com.blackmirror.dongda.domain.model.PhoneLoginBean
import com.blackmirror.dongda.domain.model.SendSmsBean
import com.blackmirror.dongda.domain.model.UpLoadWeChatIconDomainBean
import com.blackmirror.dongda.domain.model.WeChatLoginBean
import com.blackmirror.dongda.domain.repository.LoginRepository
import com.blackmirror.dongda.utils.AYPrefUtils
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-06
 */
class LoginRepositoryImpl : LoginRepository {

    override fun sendSms(phone: String): Observable<SendSmsBean> {
        return sendSMS(phone).map { it ->
            val bean = SendSmsBean()
            if (it.status == "ok") {
                bean.isSuccess = true
                if (it.result != null && it.result.reg != null) {
                    bean.phone = it.result.reg.phone
                    //                                bean.code=responseBean.result.reg.code;
                    bean.reg_token = it.result.reg.reg_token
                    bean.is_reg = it.result.reg.is_reg
                }
            } else {
                bean.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
                bean.message = it.error?.message ?: ""
            }
            return@map bean
        }
    }

    override fun phoneLogin(phone: String, code: String, reg_token: String): Observable<PhoneLoginBean> {
        val bean= PhoneLoginRequestBean()
        val json ="{\"phone\":\"$phone\",\"code\":\"$code\",\"reg_token\":\"$reg_token\"}"
        bean.json = json
        bean.url = DataConstant.AUTH_SMS_CODE_URL
        return execute(bean, PhoneLoginResponseBean::class.java)
                .map { responseBean->
                    val bean = PhoneLoginBean()
                    if ("ok"==responseBean.status) {
                        bean.isSuccess = true
                        if (responseBean.result != null) {
                            bean.auth_token = responseBean.result.auth_token
                        }
                        if (responseBean.result != null && responseBean.result.user != null) {
                            bean.screen_name = responseBean.result.user.screen_name
                            bean.has_auth_phone = responseBean.result.user.has_auth_phone
                            bean.current_device_type = responseBean.result.user.current_device_type
                            bean.is_service_provider = responseBean.result.user.is_service_provider
                            bean.user_id = responseBean.result.user.user_id
                            bean.screen_photo = responseBean.result.user.screen_photo
                            bean.current_device_id = responseBean.result.user.current_device_id
                        }
                    } else {
                        bean.code = responseBean.error?.code?:DataConstant.NET_UNKNOWN_ERROR
                        bean.message = responseBean.error?.message?:""

                    }
                    //插入数据库
                    if (bean.isSuccess) {
                        AYPrefUtils.setUserId(bean.user_id)
                        AYPrefUtils.setAuthToken(bean.auth_token)

                         val dbBean = UserInfoDbBean()
                        dbBean.is_current = 1//目前没什么卵用
                        dbBean.screen_name = bean.screen_name
                        dbBean.screen_photo = bean.screen_photo
                        dbBean.user_id = bean.user_id
                        dbBean.auth_token = bean.auth_token
                        DbRepository.insertProfile(dbBean)
                    }
                    return@map bean
                }
    }

    override fun weChatLogin(provide_uid: String, provide_token: String, provide_screen_name: String, provide_name: String, provide_screen_photo: String): Observable<WeChatLoginBean> {
        val bean = WeChatLoginRequestBean()
        val json = "{\"third\":{\"provide_uid\":\"$provide_uid\",\"provide_token\":\"$provide_token\",\"provide_screen_name\":\"$provide_screen_name\",\"provide_name\":\"$provide_name\",\"provide_screen_photo\":\"$provide_screen_photo\"}}"
        bean.json = json
        bean.url = DataConstant.WECHAT_LOGIN_URL
        return execute(bean, WeChatLoginResponseBean::class.java)
                .map { responseBean->
                    val bean =  WeChatLoginBean()

                    if ("ok"==responseBean.status) {
                        bean.isSuccess = true
                        if (responseBean.result != null) {
                            bean.auth_token = responseBean.result.auth_token
                            if (responseBean.result.user != null) {
                                bean.screen_name = responseBean.result.user.screen_name
                                bean.has_auth_phone = responseBean.result.user.has_auth_phone
                                bean.current_device_type = responseBean.result.user.current_device_type
                                bean.is_service_provider = responseBean.result.user.is_service_provider
                                bean.user_id = responseBean.result.user.user_id
                                bean.screen_photo = responseBean.result.user.screen_photo
                                bean.current_device_id = responseBean.result.user.current_device_id
                            }
                        }

                    } else {
                        if (responseBean.error != null) {
                            bean.code = responseBean.error.code
                            bean.message = responseBean.error.message
                        }
                    }
                    //插入数据库
                    if (bean.isSuccess) {

                        AYPrefUtils.setUserId(bean.user_id)
                        AYPrefUtils.setAuthToken(bean.auth_token)

                        val dbBean =  UserInfoDbBean()
                        dbBean.is_current = 1//目前没什么卵用
                        dbBean.screen_name = bean.screen_name
                        dbBean.screen_photo = bean.screen_photo
                        dbBean.user_id = bean.user_id
                        dbBean.auth_token = bean.auth_token
                        DbRepository.insertProfile(dbBean)
                    }

                    return@map bean
                }
    }

    override fun upLoadWeChatIcon(userIcon: String, imgUUID: String): Observable<UpLoadWeChatIconDomainBean> {
        val requestBean = UploadImageRequestBean()
        requestBean.userIcon = userIcon
        requestBean.imgUUID = imgUUID
        return Observable.just("").map { responseBean->
            UpLoadWeChatIconDomainBean()
        }
    }


    /*
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
     }*/
}
