package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.CommonModule;
import com.blackmirror.dongda.presenter.NearServicePresenter;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.ui.activity.NearServiceActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Create By Ruge at 2018-05-11
 */
@Component(modules = CommonModule.class)
public interface NearServiceComponent {

    NearServicePresenter getNearServicePresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(NearServiceActivity activity);
        @BindsInstance
        Builder view(Contract.NearServiceView view);
        NearServiceComponent build();
    }
}
