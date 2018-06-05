package com.blackmirror.dongda.kdomain.repository;


import com.blackmirror.dongda.kdomain.model.ApplyServiceDomainBean;
import com.blackmirror.dongda.kdomain.model.BrandAllLocDomainBean;
import com.blackmirror.dongda.kdomain.model.EnrolDomainBean;
import com.blackmirror.dongda.kdomain.model.LocAllServiceDomainBean;

import io.reactivex.Observable;


/**
 * Create By Ruge at 2018-05-15
 */
public interface ApplyAndEnrolRepository extends Repository {

    Observable<ApplyServiceDomainBean> apply(String brand_name, String name, String category, String phone, String city);

    Observable<BrandAllLocDomainBean> getBrandAllLocation(String brand_id);

    Observable<LocAllServiceDomainBean> getLocAllService(String json, String locations);

    Observable<EnrolDomainBean> enrol(String json);

}
