package com.blackmirror.dongda.kdomain.Interactor;


import com.blackmirror.dongda.kdomain.base.UseCase;
import com.blackmirror.dongda.kdomain.model.HomepageDomainBean;
import com.blackmirror.dongda.kdomain.repository.CommonRepository;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public class HomeUseCase implements UseCase<HomepageDomainBean> {
    private final CommonRepository repository;

    public HomeUseCase(CommonRepository repository) {
        this.repository = repository;
    }

    public Observable<HomepageDomainBean> getHomePageData(){
        return repository.getHomePageData();
    }


}
