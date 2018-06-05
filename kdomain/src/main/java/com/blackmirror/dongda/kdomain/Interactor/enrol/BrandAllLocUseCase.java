package com.blackmirror.dongda.kdomain.Interactor.enrol;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.BrandAllLocDomainBean;
import com.blackmirror.dongda.kdomain.repository.ApplyAndEnrolRepository;

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
