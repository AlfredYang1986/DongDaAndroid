package com.blackmirror.dongda.data.repository;

import com.blackmirror.dongda.data.net.UpdateUserInfoApi;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.domain.repository.UpdateUserInfoRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-10
 */
public class UpdateUserInfoRepositoryImpl implements UpdateUserInfoRepository {

    @Override
    public Observable<UpdateUserInfoBean> updateUserInfo(UpdateUserInfoDomainBean bean) {
        return UpdateUserInfoApi.updateUserInfo(bean);
    }
}
