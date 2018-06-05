package com.blackmirror.dongda.kdomain.Interactor;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean;
import com.blackmirror.dongda.kdomain.repository.CommonRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public class GetMoreDataUseCase implements UseCase<CareMoreDomainBean> {

    private final CommonRepository repository;

    public GetMoreDataUseCase(CommonRepository repository) {
        this.repository = repository;
    }

    public Observable<CareMoreDomainBean> getServiceMoreData(int skipCount, int takeCount, String serviceType){
        return repository.getServiceMoreData(skipCount, takeCount, serviceType);
    }
}
