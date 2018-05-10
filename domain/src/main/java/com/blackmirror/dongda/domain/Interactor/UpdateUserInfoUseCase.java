package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UseCase;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.domain.repository.UpdateUserInfoRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-10
 */
public class UpdateUserInfoUseCase implements UseCase<UpdateUserInfoBean>{

    private final UpdateUserInfoRepository repository;

    public UpdateUserInfoUseCase(UpdateUserInfoRepository repository) {
        this.repository = repository;
    }


    public Observable<UpdateUserInfoBean> weChatLogin(UpdateUserInfoDomainBean bean) {
        return repository.updateUserInfo(bean);
    }
}
