package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.CommonModule;
import com.blackmirror.dongda.presenter.HomePresenter;
import com.blackmirror.dongda.ui.activity.homeActivity.AYHomeActivity;
import com.blackmirror.dongda.ui.activity.homeActivity.HomeContract;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Create By Ruge at 2018-05-11
 */
@Component(modules = CommonModule.class)
public interface AYHomeComponent {

    HomePresenter getHomePresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(AYHomeActivity activity);
        @BindsInstance
        Builder view(HomeContract.HomeView view);
        AYHomeComponent build();
    }
}
