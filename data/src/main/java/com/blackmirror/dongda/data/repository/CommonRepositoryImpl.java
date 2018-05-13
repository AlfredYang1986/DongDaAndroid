package com.blackmirror.dongda.data.repository;

import android.text.TextUtils;

import com.blackmirror.dongda.data.model.response.CareMoreResponseBean;
import com.blackmirror.dongda.data.model.response.LikePushResponseBean;
import com.blackmirror.dongda.data.model.response.SearchServiceResponseBean;
import com.blackmirror.dongda.data.net.CommonApi;
import com.blackmirror.dongda.domain.model.CareMoreDomainBean;
import com.blackmirror.dongda.domain.model.HomepageDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.domain.repository.CommonRepository;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Create By Ruge at 2018-05-11
 */
public class CommonRepositoryImpl implements CommonRepository {
    @Override
    public Observable<HomepageDomainBean> getHomePageData() {
        return CommonApi.searchService()
                .map(new Function<SearchServiceResponseBean, HomepageDomainBean>() {
                    @Override
                    public HomepageDomainBean apply(SearchServiceResponseBean bean) throws Exception {
                        HomepageDomainBean domainBean = new HomepageDomainBean();
                        tran2DomainBean(bean, domainBean);
                        return domainBean;
                    }
                });
    }

    @Override
    public Observable<LikePushDomainBean> likePush(String service_id) {
        return CommonApi.likePush(service_id)
                .map(new Function<LikePushResponseBean, LikePushDomainBean>() {
                    @Override
                    public LikePushDomainBean apply(LikePushResponseBean bean) throws Exception {
                        LikePushDomainBean b = new LikePushDomainBean();
                        if (bean == null || bean.result == null) {
                            return b;
                        }
                        if ("ok".equals(bean.status)) {
                            b.isSuccess = true;
                        } else {
                            if (bean.error != null) {
                                b.code = bean.error.code;
                                b.message = bean.error.message;
                            }
                        }
                        return b;
                    }
                });
    }

    @Override
    public Observable<LikePopDomainBean> likePop(String service_id) {
        return CommonApi.likePop(service_id)
                .map(new Function<LikePushResponseBean, LikePopDomainBean>() {
                    @Override
                    public LikePopDomainBean apply(LikePushResponseBean bean) throws Exception {
                        LikePopDomainBean b = new LikePopDomainBean();
                        if (bean == null || bean.result == null) {
                            return b;
                        }
                        if ("ok".equals(bean.status)) {
                            b.isSuccess = true;
                        } else {
                            if (bean.error != null) {
                                b.code = bean.error.code;
                                b.message = bean.error.message;
                            }
                        }
                        return b;
                    }
                });
    }

    @Override
    public Observable<CareMoreDomainBean> getServiceMoreData(int skipCount, int takeCount, String serviceType) {
        return CommonApi.getServiceMoreData(skipCount, takeCount, serviceType)
                .map(new Function<CareMoreResponseBean, CareMoreDomainBean>() {
                    @Override
                    public CareMoreDomainBean apply(CareMoreResponseBean bean) throws Exception {
                        CareMoreDomainBean domainBean = new CareMoreDomainBean();
                        tran2CareMoreDomainBean(bean, domainBean);
                        return domainBean;
                    }
                });
    }

    private void tran2CareMoreDomainBean(CareMoreResponseBean bean, CareMoreDomainBean domainBean) {
        if (bean == null) {
            return;
        }

        if (TextUtils.isEmpty(bean.status) || !"ok".equals(bean.status)) {
            if (bean.error == null) {
                return;
            }
            domainBean.code = bean.error.code;
            domainBean.message = bean.error.message;

        }

        domainBean.isSuccess = true;

        domainBean.services = new ArrayList<>();
        if (bean.result == null || bean.result.services == null) {
            return;
        }

        for (int i = 0; i < bean.result.services.size(); i++) {
            CareMoreResponseBean.ResultBean.ServicesBean sb = bean.result.services.get(i);
            CareMoreDomainBean.ServicesBean b = new CareMoreDomainBean.ServicesBean();

            b.is_collected=sb.is_collected;
            b.punchline=sb.punchline;
            b.service_leaf=sb.service_leaf;
            b.brand_id=sb.brand_id;
            b.location_id=sb.location_id;
            b.service_image=sb.service_image;
            b.brand_name=sb.brand_name;
            b.service_type=sb.service_type;
            b.address=sb.address;
            b.category=sb.category;
            CareMoreDomainBean.ServicesBean.PinBean pin = new CareMoreDomainBean.ServicesBean.PinBean();
            if (sb.pin != null) {
                pin.latitude = sb.pin.latitude;
                pin.longitude = sb.pin.longitude;
                b.pin = pin;
            } else {
                b.pin = pin;
            }

            b.service_id=sb.service_id;

            b.service_tags = sb.service_tags != null ? sb.service_tags : new ArrayList<String>();
            b.operation = sb.operation != null ? sb.operation : new ArrayList<String>();

            domainBean.services.add(b);

        }

    }

    private void tran2DomainBean(SearchServiceResponseBean bean, HomepageDomainBean domainBean) {
        if (bean == null) {
            return;
        }

        if (TextUtils.isEmpty(bean.status) || !"ok".equals(bean.status)) {
            if (bean.error == null) {
                return;
            }
            domainBean.code = bean.error.code;
            domainBean.message = bean.error.message;

        }

        domainBean.isSuccess = true;

        domainBean.homepage_services = new ArrayList<>();
        if (bean.result == null || bean.result.homepage_services == null) {
            return;
        }

        for (int i = 0; i < bean.result.homepage_services.size(); i++) {
            SearchServiceResponseBean.ResultBean.HomepageServicesBean sb = bean.result.homepage_services.get(i);
            HomepageDomainBean.HomepageServicesBean b = new HomepageDomainBean.HomepageServicesBean();
            b.service_type = sb.service_type;
            b.totalCount = sb.totalCount;

            if (sb.services == null) {
                domainBean.homepage_services.add(b);
                continue;
            }

            b.services = new ArrayList<>();

            for (int j = 0; j < sb.services.size(); j++) {
                SearchServiceResponseBean.ResultBean.HomepageServicesBean.ServicesBean s = sb.services.get(j);
                HomepageDomainBean.HomepageServicesBean.ServicesBean d = new HomepageDomainBean.HomepageServicesBean.ServicesBean();
                d.is_collected = s.is_collected;
                d.service_leaf = s.service_leaf;
                d.brand_id = s.brand_id;
                d.location_id = s.location_id;
                d.service_image = s.service_image;
                d.brand_name = s.brand_name;
                d.service_type = s.service_type;
                d.address = s.address;
                d.category = s.category;
                d.service_id = s.service_id;

                HomepageDomainBean.HomepageServicesBean.ServicesBean.PinBean pin = new HomepageDomainBean.HomepageServicesBean.ServicesBean.PinBean();
                if (s.pin != null) {
                    pin.latitude = s.pin.latitude;
                    pin.longitude = s.pin.longitude;
                    d.pin = pin;
                } else {
                    d.pin = pin;
                }

                d.service_tags = s.service_tags != null ? s.service_tags : new ArrayList<String>();
                d.operation = s.operation != null ? s.operation : new ArrayList<String>();

                b.services.add(d);

            }

            domainBean.homepage_services.add(b);
        }

    }
}