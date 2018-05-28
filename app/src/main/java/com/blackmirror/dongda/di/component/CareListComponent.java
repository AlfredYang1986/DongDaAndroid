package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.CommonModule;
import com.blackmirror.dongda.presenter.GetMoreDataPresenter;
import com.blackmirror.dongda.ui.activity.CareListActivity;
import com.blackmirror.dongda.ui.activity.ListMoreContract;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Create By Ruge at 2018-05-11
 */
@Component(modules = CommonModule.class)
public interface CareListComponent {

    GetMoreDataPresenter getMoreDataPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(CareListActivity activity);
        @BindsInstance
        Builder view(ListMoreContract.View view);
        CareListComponent build();
    }
}
