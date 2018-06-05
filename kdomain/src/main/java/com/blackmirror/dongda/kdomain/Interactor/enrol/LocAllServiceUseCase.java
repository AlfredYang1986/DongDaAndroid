package com.blackmirror.dongda.kdomain.Interactor.enrol;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.LocAllServiceDomainBean;
import com.blackmirror.dongda.kdomain.repository.ApplyAndEnrolRepository;

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
