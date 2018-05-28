package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UseCase;
import com.blackmirror.dongda.domain.model.ApplyServiceDomainBean;
import com.blackmirror.dongda.domain.model.HomepageDomainBean;
import com.blackmirror.dongda.domain.repository.ApplyAndEnrolRepository;

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
