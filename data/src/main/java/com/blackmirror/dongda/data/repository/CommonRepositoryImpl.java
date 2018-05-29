package com.blackmirror.dongda.data.repository;

import android.text.TextUtils;

import com.blackmirror.dongda.data.model.response.CareMoreResponseBean;
import com.blackmirror.dongda.data.model.response.DetailInfoResponseBean;
import com.blackmirror.dongda.data.model.response.LikePopResponseBean;
import com.blackmirror.dongda.data.model.response.LikePushResponseBean;
import com.blackmirror.dongda.data.model.response.LikeResponseBean;
import com.blackmirror.dongda.data.model.response.NearServiceResponseBean;
import com.blackmirror.dongda.data.model.response.SearchServiceResponseBean;
import com.blackmirror.dongda.data.net.CommonApi;
import com.blackmirror.dongda.domain.model.CareMoreDomainBean;
import com.blackmirror.dongda.domain.model.DetailInfoDomainBean;
import com.blackmirror.dongda.domain.model.HomepageDomainBean;
import com.blackmirror.dongda.domain.model.LikeDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.domain.model.NearServiceDomainBean;
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
                .map(new Function<LikePopResponseBean, LikePopDomainBean>() {
                    @Override
                    public LikePopDomainBean apply(LikePopResponseBean bean) throws Exception {
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
    public Observable<LikeDomainBean> getLikeData() {
        return CommonApi.getLikeData()
                .map(new Function<LikeResponseBean, LikeDomainBean>() {
                    @Override
                    public LikeDomainBean apply(LikeResponseBean bean) throws Exception {
                        LikeDomainBean domainBean = new LikeDomainBean();
                        tran2LikeDomainBean(bean, domainBean);
                        return domainBean;
                    }
                });
    }

    @Override
    public Observable<NearServiceDomainBean> getNearService(double latitude, double longitude) {
        return CommonApi.getNearService(latitude, longitude)
                .map(new Function<NearServiceResponseBean, NearServiceDomainBean>() {
                    @Override
                    public NearServiceDomainBean apply(NearServiceResponseBean bean) throws Exception {
                        NearServiceDomainBean domainBean = new NearServiceDomainBean();
                        tran2NearServiceDomainBean(bean, domainBean);
                        return domainBean;
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

    @Override
    public Observable<DetailInfoDomainBean> getDetailInfo(String service_id) {
        return CommonApi.getDetailInfo(service_id)
                .map(new Function<DetailInfoResponseBean, DetailInfoDomainBean>() {
                    @Override
                    public DetailInfoDomainBean apply(DetailInfoResponseBean bean) throws Exception {
                        DetailInfoDomainBean domainBean = new DetailInfoDomainBean();
                        tran2DetailInfoBean(bean, domainBean);
                        return domainBean;
                    }
                });
    }

    private void tran2DetailInfoBean(DetailInfoResponseBean bean, DetailInfoDomainBean domainBean) {
        if (bean == null) {
            return;
        }

        if (TextUtils.isEmpty(bean.status) || !"ok".equals(bean.status)) {
            if (bean.error == null) {
                return;
            }
            domainBean.code = bean.error.code;
            domainBean.message = bean.error.message;
            return;
        }


        if (bean.result == null || bean.result.service == null) {
            return;
        }

        domainBean.isSuccess = true;

        DetailInfoResponseBean.ResultBean.ServiceBean service = bean.result.service;
        domainBean.class_max_stu = service.class_max_stu;
        if (service.location != null) {
            domainBean.location_id = service.location.location_id;
            domainBean.address = service.location.address;
            domainBean.friendliness = service.location.friendliness == null ? new ArrayList<String>() : service.location.friendliness;
        }

        if (service.location !=null && service.location.pin!=null){
            domainBean.latitude = service.location.pin.latitude;
            domainBean.longitude = service.location.pin.longitude;
        }

        domainBean.location_images = new ArrayList<>();

        if (service.location != null && service.location.location_images != null) {
            for (int i = 0; i < service.location.location_images.size(); i++) {
                DetailInfoDomainBean.LocationImagesBean dlb = new DetailInfoDomainBean.LocationImagesBean();
                DetailInfoResponseBean.ResultBean.ServiceBean.LocationBean.LocationImagesBean lb = service.location.location_images.get(i);
                dlb.tag = lb.tag;
                dlb.image = lb.image;
                domainBean.location_images.add(dlb);
            }
        }
        domainBean.is_collected = service.is_collected;
        domainBean.description = service.description;
        domainBean.min_age = service.min_age;
        domainBean.punchline = service.punchline;
        domainBean.teacher_num = service.teacher_num;
        domainBean.service_leaf = service.service_leaf;

        if (service.brand != null) {

            domainBean.brand_id = service.brand.brand_id;
            domainBean.date = service.brand.date;
            domainBean.brand_name = service.brand.brand_name;
            domainBean.brand_tag = service.brand.brand_tag;
            domainBean.about_brand = service.brand.about_brand;
        }

        domainBean.max_age = service.max_age;
        domainBean.service_type = service.service_type;
        domainBean.category = service.category;
        domainBean.album = service.album;
        domainBean.service_id = service.service_id;
        domainBean.service_tags = service.service_tags == null ? new ArrayList<String>() : service.service_tags;
        domainBean.operation = service.operation == null ? new ArrayList<String>() : service.operation;

        domainBean.service_images = new ArrayList<>();
        if (service.service_images!=null){
            for (int i = 0; i < service.service_images.size(); i++) {
                DetailInfoDomainBean.ServiceImagesBean dsb = new DetailInfoDomainBean.ServiceImagesBean();
                DetailInfoResponseBean.ResultBean.ServiceBean.ServiceImagesBean sb = service.service_images.get(i);
                dsb.tag = sb.tag;
                dsb.image = sb.image;
                domainBean.service_images.add(dsb);
            }
        }




    }

    private void tran2NearServiceDomainBean(NearServiceResponseBean bean, NearServiceDomainBean domainBean) {
        if (bean == null) {
            return;
        }

        if (TextUtils.isEmpty(bean.status) || !"ok".equals(bean.status)) {
            if (bean.error == null) {
                return;
            }
            domainBean.code = bean.error.code;
            domainBean.message = bean.error.message;
            return;

        }

        domainBean.isSuccess = true;

        domainBean.services = new ArrayList<>();
        if (bean.result == null || bean.result.services == null) {
            return;
        }

        for (int i = 0; i < bean.result.services.size(); i++) {
            NearServiceResponseBean.ResultBean.ServicesBean sb = bean.result.services.get(i);
            NearServiceDomainBean.ServicesBean b = new NearServiceDomainBean.ServicesBean();

            b.is_collected = sb.is_collected;
            b.punchline = sb.punchline;
            b.service_leaf = sb.service_leaf;
            b.brand_id = sb.brand_id;
            b.location_id = sb.location_id;
            b.service_image = sb.service_image;
            b.brand_name = sb.brand_name;
            b.service_type = sb.service_type;
            b.address = sb.address;
            b.category = sb.category;
            NearServiceDomainBean.ServicesBean.PinBean pin = new NearServiceDomainBean.ServicesBean.PinBean();
            if (sb.pin != null) {
                pin.latitude = sb.pin.latitude;
                pin.longitude = sb.pin.longitude;
                b.pin = pin;
            } else {
                b.pin = pin;
            }

            b.service_id = sb.service_id;

            b.service_tags = sb.service_tags != null ? sb.service_tags : new ArrayList<String>();
            b.operation = sb.operation != null ? sb.operation : new ArrayList<String>();

            domainBean.services.add(b);

        }

    }

    private void tran2LikeDomainBean(LikeResponseBean bean, LikeDomainBean domainBean) {
        if (bean == null) {
            return;
        }

        if (TextUtils.isEmpty(bean.status) || !"ok".equals(bean.status)) {
            if (bean.error == null) {
                return;
            }
            domainBean.code = bean.error.code;
            domainBean.message = bean.error.message;
            return;

        }

        domainBean.isSuccess = true;

        domainBean.services = new ArrayList<>();
        if (bean.result == null || bean.result.services == null) {
            return;
        }

        for (int i = 0; i < bean.result.services.size(); i++) {
            LikeResponseBean.ResultBean.ServicesBean sb = bean.result.services.get(i);
            LikeDomainBean.ServicesBean b = new LikeDomainBean.ServicesBean();

            b.is_collected = sb.is_collected;
            b.punchline = sb.punchline;
            b.service_leaf = sb.service_leaf;
            b.brand_id = sb.brand_id;
            b.location_id = sb.location_id;
            b.service_image = sb.service_image;
            b.brand_name = sb.brand_name;
            b.service_type = sb.service_type;
            b.address = sb.address;
            b.category = sb.category;
            LikeDomainBean.ServicesBean.PinBean pin = new LikeDomainBean.ServicesBean.PinBean();
            if (sb.pin != null) {
                pin.latitude = sb.pin.latitude;
                pin.longitude = sb.pin.longitude;
                b.pin = pin;
            } else {
                b.pin = pin;
            }

            b.service_id = sb.service_id;

            b.service_tags = sb.service_tags != null ? sb.service_tags : new ArrayList<String>();
            b.operation = sb.operation != null ? sb.operation : new ArrayList<String>();

            domainBean.services.add(b);

        }

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
            return;

        }

        domainBean.isSuccess = true;

        domainBean.services = new ArrayList<>();
        if (bean.result == null || bean.result.services == null) {
            return;
        }

        for (int i = 0; i < bean.result.services.size(); i++) {
            CareMoreResponseBean.ResultBean.ServicesBean sb = bean.result.services.get(i);
            CareMoreDomainBean.ServicesBean b = new CareMoreDomainBean.ServicesBean();

            b.is_collected = sb.is_collected;
            b.punchline = sb.punchline;
            b.service_leaf = sb.service_leaf;
            b.brand_id = sb.brand_id;
            b.location_id = sb.location_id;
            b.service_image = sb.service_image;
            b.brand_name = sb.brand_name;
            b.service_type = sb.service_type;
            b.address = sb.address;
            b.category = sb.category;
            CareMoreDomainBean.ServicesBean.PinBean pin = new CareMoreDomainBean.ServicesBean.PinBean();
            if (sb.pin != null) {
                pin.latitude = sb.pin.latitude;
                pin.longitude = sb.pin.longitude;
                b.pin = pin;
            } else {
                b.pin = pin;
            }

            b.service_id = sb.service_id;

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
            return;
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
