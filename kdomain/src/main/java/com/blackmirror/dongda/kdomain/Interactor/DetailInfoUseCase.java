package com.blackmirror.dongda.kdomain.Interactor;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean;
import com.blackmirror.dongda.kdomain.model.DetailInfoDomainBean;
import com.blackmirror.dongda.kdomain.repository.CommonRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public class DetailInfoUseCase implements UseCase<CareMoreDomainBean> {

    private final CommonRepository repository;

    public DetailInfoUseCase(CommonRepository repository) {
        this.repository = repository;
    }

    public Observable<DetailInfoDomainBean> getDetailInfo(String service_id){
        return repository.getDetailInfo(service_id);
    }
}
