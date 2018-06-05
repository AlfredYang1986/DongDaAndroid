package com.blackmirror.dongda.kdomain.Interactor;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.ApplyServiceDomainBean;
import com.blackmirror.dongda.kdomain.model.HomepageDomainBean;
import com.blackmirror.dongda.kdomain.repository.ApplyAndEnrolRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public class ApplyServiceUseCase implements UseCase<HomepageDomainBean> {
    private final ApplyAndEnrolRepository repository;

    public ApplyServiceUseCase(ApplyAndEnrolRepository repository) {
        this.repository = repository;
    }

    public Observable<ApplyServiceDomainBean> apply(String brand_name, String name, String category, String phone, String city){
        return repository.apply(brand_name, name, category, phone, city);
    }


}
