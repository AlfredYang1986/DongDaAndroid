package com.blackmirror.dongda.kdomain.Interactor;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean;
import com.blackmirror.dongda.kdomain.model.NearServiceDomainBean;
import com.blackmirror.dongda.kdomain.repository.CommonRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public class NearServiceUseCase implements UseCase<CareMoreDomainBean> {

    private final CommonRepository repository;

    public NearServiceUseCase(CommonRepository repository) {
        this.repository = repository;
    }

    public Observable<NearServiceDomainBean> getNearService(double latitude, double longitude){
        return repository.getNearService(latitude, longitude);
    }
}
