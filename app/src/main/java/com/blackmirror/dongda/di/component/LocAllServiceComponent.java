package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.ApplyAndEnrolModule;
import com.blackmirror.dongda.presenter.EnrolPresenter;
import com.blackmirror.dongda.ui.activity.enrol.EnrolContract;
import com.blackmirror.dongda.ui.activity.enrol.LocAllServiceActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by xcx on 2018/5/9.
 */

@Component(modules = ApplyAndEnrolModule.class)
public interface LocAllServiceComponent {

    EnrolPresenter getEnrolPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(LocAllServiceActivity activity);
        @BindsInstance
        Builder view(EnrolContract.View view);
        LocAllServiceComponent build();

    }
}
