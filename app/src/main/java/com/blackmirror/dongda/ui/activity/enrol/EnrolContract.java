package com.blackmirror.dongda.ui.activity.enrol;


import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.BrandAllLocDomainBean;
import com.blackmirror.dongda.domain.model.LocAllServiceDomainBean;
import com.blackmirror.dongda.ui.BasePresenter;

public class EnrolContract {
    public interface View{

        void onGetBrandAllLocationSuccess(BrandAllLocDomainBean bean);

        void onGetLocAllServiceSuccess(LocAllServiceDomainBean bean);

        void onError(BaseDataBean bean);

    }

    public interface Presenter extends BasePresenter<View> {
        void getBrandAllLocation(String brand_id);
        void getLocAllService(String json, String locations);
    }
}
