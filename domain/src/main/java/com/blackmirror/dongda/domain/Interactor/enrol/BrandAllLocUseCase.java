package com.blackmirror.dongda.domain.Interactor.enrol;

import com.blackmirror.dongda.domain.base.UseCase;
import com.blackmirror.dongda.domain.model.BrandAllLocDomainBean;
import com.blackmirror.dongda.domain.repository.ApplyAndEnrolRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-15
 */
public class BrandAllLocUseCase implements UseCase<BrandAllLocDomainBean> {

    private final ApplyAndEnrolRepository repository;

    public BrandAllLocUseCase(ApplyAndEnrolRepository repository) {
        this.repository = repository;
    }

    public Observable<BrandAllLocDomainBean> getBrandAllLocation(String brand_id){
        return repository.getBrandAllLocation(brand_id);
    }

}
