package com.blackmirror.dongda.kdomain.Interactor;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.LikePopDomainBean;
import com.blackmirror.dongda.kdomain.repository.CommonRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public class LikePopUseCase implements UseCase<LikePopDomainBean> {

    private final CommonRepository repository;

    public LikePopUseCase(CommonRepository repository) {
        this.repository = repository;
    }

    public Observable<LikePopDomainBean> likePop(String service_id){
        return repository.likePop(service_id);
    }
}
