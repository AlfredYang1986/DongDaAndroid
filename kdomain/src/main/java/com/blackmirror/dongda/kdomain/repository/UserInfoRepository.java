package com.blackmirror.dongda.kdomain.repository;


import com.blackmirror.dongda.kdomain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.kdomain.model.UserInfoDomainBean;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-10
 */
public interface UserInfoRepository extends Repository {

    Observable<UserInfoDomainBean> queryUserInfo();

    Observable<UpdateUserInfoBean> updateUserInfo(UpdateUserInfoDomainBean bean);

}
