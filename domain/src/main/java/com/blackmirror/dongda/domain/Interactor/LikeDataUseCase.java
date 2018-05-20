package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UseCase;
import com.blackmirror.dongda.domain.model.LikeDomainBean;
import com.blackmirror.dongda.domain.repository.CommonRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public class LikeDataUseCase implements UseCase<LikeDomainBean> {

    private final CommonRepository repository;

    public LikeDataUseCase(CommonRepository repository) {
        this.repository = repository;
    }

    public Observable<LikeDomainBean> getLikeData(){
        return repository.getLikeData();
    }
}
