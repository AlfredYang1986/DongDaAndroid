package com.blackmirror.dongda.domain.repository;

import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.domain.model.UserInfoDomainBean;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-10
 */
public interface UserInfoRepository extends Repository {

    Observable<UserInfoDomainBean> queryUserInfo();

    Observable<UpdateUserInfoBean> updateUserInfo(UpdateUserInfoDomainBean bean);

}
