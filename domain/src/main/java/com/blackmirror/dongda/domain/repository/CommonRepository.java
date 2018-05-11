package com.blackmirror.dongda.domain.repository;

import com.blackmirror.dongda.domain.model.CareMoreDomainBean;
import com.blackmirror.dongda.domain.model.HomepageDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public interface CommonRepository extends Repository {

    Observable<HomepageDomainBean> getHomePageData();

    Observable<LikePushDomainBean> likePush(String service_id);

    Observable<LikePopDomainBean> likePop(String service_id);

    Observable<CareMoreDomainBean> getServiceMoreData(int skipCount, int takeCount, String serviceType);


}
