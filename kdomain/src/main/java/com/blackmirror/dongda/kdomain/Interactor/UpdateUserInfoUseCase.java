package com.blackmirror.dongda.kdomain.Interactor;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.kdomain.repository.UserInfoRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-10
 */
public class UpdateUserInfoUseCase implements UseCase<UpdateUserInfoBean> {

    private final UserInfoRepository repository;

    public UpdateUserInfoUseCase(UserInfoRepository repository) {
        this.repository = repository;
    }


    public Observable<UpdateUserInfoBean> updateUserInfo(UpdateUserInfoDomainBean bean) {
        return repository.updateUserInfo(bean);
    }
}
