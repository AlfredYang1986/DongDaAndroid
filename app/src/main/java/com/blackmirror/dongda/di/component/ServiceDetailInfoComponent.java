package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.CommonModule;
import com.blackmirror.dongda.presenter.DetailInfoPresenter;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.ui.activity.ServiceDetailInfoActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Create By Ruge at 2018-05-18
 */
@Component(modules = CommonModule.class)
public interface ServiceDetailInfoComponent {

    DetailInfoPresenter getDetailInfoPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(ServiceDetailInfoActivity activity);
        @BindsInstance
        Builder view(Contract.DetailInfoView view);
        ServiceDetailInfoComponent build();
    }

}
