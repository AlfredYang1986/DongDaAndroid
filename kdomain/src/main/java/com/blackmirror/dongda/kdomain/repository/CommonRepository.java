package com.blackmirror.dongda.kdomain.repository;


import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean;
import com.blackmirror.dongda.kdomain.model.DetailInfoDomainBean;
import com.blackmirror.dongda.kdomain.model.HomepageDomainBean;
import com.blackmirror.dongda.kdomain.model.LikeDomainBean;
import com.blackmirror.dongda.kdomain.model.LikePopDomainBean;
import com.blackmirror.dongda.kdomain.model.LikePushDomainBean;
import com.blackmirror.dongda.kdomain.model.NearServiceDomainBean;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-11
 */
public interface CommonRepository extends Repository {

    Observable<HomepageDomainBean> getHomePageData();

    Observable<LikePushDomainBean> likePush(String service_id);

    Observable<LikePopDomainBean> likePop(String service_id);

    Observable<LikeDomainBean> getLikeData();

    Observable<NearServiceDomainBean> getNearService(double latitude, double longitude);

    Observable<CareMoreDomainBean> getServiceMoreData(int skipCount, int takeCount, String serviceType);

    Observable<DetailInfoDomainBean> getDetailInfo(String service_id);


}
