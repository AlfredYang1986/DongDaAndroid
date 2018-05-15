package com.blackmirror.dongda.data.repository;

import com.blackmirror.dongda.data.model.response.ApplyServiceResponseBean;
import com.blackmirror.dongda.data.model.response.BrandAllLocResponseBean;
import com.blackmirror.dongda.data.net.CommonApi;
import com.blackmirror.dongda.domain.model.ApplyServiceDomainBean;
import com.blackmirror.dongda.domain.model.BrandAllLocDomainBean;
import com.blackmirror.dongda.domain.repository.ApplyAndEnrolRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Create By Ruge at 2018-05-15
 */
public class ApplyAndEnrolRepositoryImpl implements ApplyAndEnrolRepository {
    @Override
    public Observable<ApplyServiceDomainBean> apply(final String brand_name, String name, String category, String phone, String city) {
        return CommonApi.apply(brand_name, name, category, phone, city)
                .map(new Function<ApplyServiceResponseBean, ApplyServiceDomainBean>() {
                    @Override
                    public ApplyServiceDomainBean apply(ApplyServiceResponseBean bean) throws Exception {
                        ApplyServiceDomainBean domainBean = new ApplyServiceDomainBean();
                        if (bean == null) {
                            return domainBean;
                        }
                        if ("ok".equals(bean.status)) {
                            domainBean.isSuccess = true;
                            domainBean.apply_id = bean.result != null ? bean.result.apply_id : "";
                        } else {
                            domainBean.code = bean.error != null ? bean.error.code : domainBean.code;
                            domainBean.message = bean.error != null ? bean.error.message : "";
                        }
                        return domainBean;
                    }
                });
    }

    @Override
    public Observable<BrandAllLocDomainBean> getBrandAllLocation(String brand_id) {
        return CommonApi.getBrandAllLocation(brand_id)
                .map(new Function<BrandAllLocResponseBean, BrandAllLocDomainBean>() {
                    @Override
                    public BrandAllLocDomainBean apply(BrandAllLocResponseBean bean) throws Exception {
                        BrandAllLocDomainBean domainBean = new BrandAllLocDomainBean();
                        transLoc2DomainBean(bean, domainBean);
                        return domainBean;
                    }
                });
    }

    private void transLoc2DomainBean(BrandAllLocResponseBean bean, BrandAllLocDomainBean domainBean) {
        if (bean == null) {
            return;
        }
        if (!"ok".equals(bean.status)) {
            domainBean.isSuccess = true;
            domainBean.code = bean.error != null ? bean.error.code : domainBean.code;
            domainBean.message = bean.error != null ? bean.error.message : domainBean.message;
        }
        List<BrandAllLocDomainBean.LocationsBean> locations = new ArrayList<>();
        domainBean.locations = locations;
        if (bean.result == null) {
            return;
        }

        for (int i = 0; i < bean.result.locations.size(); i++) {
            BrandAllLocResponseBean.ResultBean.LocationsBean lb = bean.result.locations.get(i);
            BrandAllLocDomainBean.LocationsBean dlb = new BrandAllLocDomainBean.LocationsBean();

            dlb.location_id = lb.location_id;
            dlb.address = lb.address;

            BrandAllLocDomainBean.LocationsBean.PinBean pin = new BrandAllLocDomainBean.LocationsBean.PinBean();
            if (lb.pin!=null){
                pin.latitude=lb.pin.latitude;
                pin.longitude=lb.pin.longitude;
            }
            dlb.pin = pin;

            dlb.friendliness = lb.friendliness == null ? new ArrayList<String>() : lb.friendliness;

            dlb.location_images = new ArrayList<>();
            if (lb.location_images==null){
                domainBean.locations.add(dlb);
                continue;
            }

            for (int j = 0; j < lb.location_images.size(); j++) {
                BrandAllLocResponseBean.ResultBean.LocationsBean.LocationImagesBean ib = lb.location_images.get(j);
                BrandAllLocDomainBean.LocationsBean.LocationImagesBean dib = new BrandAllLocDomainBean.LocationsBean.LocationImagesBean();
                dib.image = ib.image;
                dib.tag = ib.tag;
                dlb.location_images.add(dib);
            }

            domainBean.locations.add(dlb);

        }
    }
}
