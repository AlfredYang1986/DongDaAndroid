package com.blackmirror.dongda.domain.Interactor.enrol;

import com.blackmirror.dongda.domain.base.UseCase;
import com.blackmirror.dongda.domain.model.EnrolDomainBean;
import com.blackmirror.dongda.domain.repository.ApplyAndEnrolRepository;

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
