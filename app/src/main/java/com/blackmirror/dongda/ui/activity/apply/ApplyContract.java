package com.blackmirror.dongda.ui.activity.apply;


import com.blackmirror.dongda.domain.model.ApplyServiceDomainBean;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.ui.BasePresenter;

public class ApplyContract {
    public interface View{
        void onApplySuccess(ApplyServiceDomainBean bean);

        void onError(BaseDataBean bean);

    }

    public interface Presenter extends BasePresenter<View> {
        void apply(String brand_name, String name, String category, String phone, String city);
    }
}
