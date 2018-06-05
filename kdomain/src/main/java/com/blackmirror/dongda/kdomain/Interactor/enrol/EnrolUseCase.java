package com.blackmirror.dongda.kdomain.Interactor.enrol;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.EnrolDomainBean;
import com.blackmirror.dongda.kdomain.repository.ApplyAndEnrolRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-15
 */
public class EnrolUseCase implements UseCase<EnrolDomainBean> {

    private final ApplyAndEnrolRepository repository;

    public EnrolUseCase(ApplyAndEnrolRepository repository) {
        this.repository = repository;
    }

    public Observable<EnrolDomainBean> enrol(String json){
        return repository.enrol(json);
    }

}
