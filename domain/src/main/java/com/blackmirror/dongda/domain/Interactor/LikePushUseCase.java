package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UseCase;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.domain.repository.CommonRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public class LikePushUseCase implements UseCase<LikePushDomainBean> {

    private final CommonRepository repository;

    public LikePushUseCase(CommonRepository repository) {
        this.repository = repository;
    }

    public Observable<LikePushDomainBean> likePush(String service_id){
        return repository.likePush(service_id);
    }
}
