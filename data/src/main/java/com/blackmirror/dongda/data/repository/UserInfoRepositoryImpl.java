package com.blackmirror.dongda.data.repository;

import com.blackmirror.dongda.data.model.response.UserInfoResponseBean;
import com.blackmirror.dongda.data.net.CommonApi;
import com.blackmirror.dongda.data.net.UpdateUserInfoApi;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.domain.model.UserInfoDomainBean;
import com.blackmirror.dongda.domain.repository.UserInfoRepository;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Create By Ruge at 2018-05-10
 */
public class UserInfoRepositoryImpl implements UserInfoRepository {

    @Override
    public Observable<UserInfoDomainBean> queryUserInfo() {
        return CommonApi.queryUserInfo()
                .map(new Function<UserInfoResponseBean, UserInfoDomainBean>() {
                    @Override
                    public UserInfoDomainBean apply(UserInfoResponseBean bean) throws Exception {
                        UserInfoDomainBean ub = new UserInfoDomainBean();
                        if (bean == null) {
                            return ub;
                        }
                        if ("ok".equals(bean.getStatus())) {
                            ub.isSuccess = true;
                            if (bean.result != null && bean.result.profile != null) {
                                ub.screen_name = bean.result.profile.getScreen_name();
                                ub.description = bean.result.profile.getDescription();
                                ub.has_auth_phone = bean.result.profile.getHas_auth_phone();
                                ub.owner_name = bean.result.profile.getOwner_name();
                                ub.is_service_provider = bean.result.profile.getIs_service_provider();
                                ub.user_id = bean.result.profile.getUser_id();
                                ub.company = bean.result.profile.getCompany();
                                ub.screen_photo = bean.result.profile.getScreen_photo();
                                ub.date = bean.result.profile.getDate();
                                ub.address = bean.result.profile.getAddress();
                                ub.contact_no = bean.result.profile.getContact_no();
                                ub.social_id = bean.result.profile.getSocial_id();
                            }
                        } else if (bean.getError() != null) {
                            ub.code = bean.getError().getCode();
                            ub.message = bean.getError().getMessage();

                        }
                        return ub;
                    }
                });
    }



    @Override
    public Observable<UpdateUserInfoBean> updateUserInfo(UpdateUserInfoDomainBean bean) {
        return UpdateUserInfoApi.updateUserInfo(bean);
    }


}
