package com.blackmirror.dongda.kdomain.Interactor.userinfo;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.UserInfoDomainBean;
import com.blackmirror.dongda.kdomain.repository.UserInfoRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-15
 */
public class QueryUserInfoUseCase implements UseCase<UserInfoDomainBean> {

    private final UserInfoRepository repository;

    public QueryUserInfoUseCase(UserInfoRepository repository) {
        this.repository = repository;
    }

    public Observable<UserInfoDomainBean> queryUserInfo(){
        return repository.queryUserInfo();
    }
}
