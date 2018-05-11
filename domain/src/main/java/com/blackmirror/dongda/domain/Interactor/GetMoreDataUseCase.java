package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UseCase;
import com.blackmirror.dongda.domain.model.CareMoreDomainBean;
import com.blackmirror.dongda.domain.repository.CommonRepository;

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
