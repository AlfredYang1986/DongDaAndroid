package com.blackmirror.dongda.domain.Interactor.enrol;

import com.blackmirror.dongda.domain.base.UseCase;
import com.blackmirror.dongda.domain.model.LocAllServiceDomainBean;
import com.blackmirror.dongda.domain.repository.ApplyAndEnrolRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-15
 */
public class LocAllServiceUseCase implements UseCase<LocAllServiceDomainBean> {

    private final ApplyAndEnrolRepository repository;

    public LocAllServiceUseCase(ApplyAndEnrolRepository repository) {
        this.repository = repository;
    }

    public Observable<LocAllServiceDomainBean> getLocAllService(String json, String locations){
        return repository.getLocAllService(json, locations);
    }

}
