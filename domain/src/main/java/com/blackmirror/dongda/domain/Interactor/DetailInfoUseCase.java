package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UseCase;
import com.blackmirror.dongda.domain.model.CareMoreDomainBean;
import com.blackmirror.dongda.domain.model.DetailInfoDomainBean;
import com.blackmirror.dongda.domain.repository.CommonRepository;

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
