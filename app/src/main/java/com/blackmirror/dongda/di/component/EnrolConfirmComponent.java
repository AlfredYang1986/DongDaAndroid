package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.ApplyAndEnrolModule;
import com.blackmirror.dongda.presenter.EnrolPresenter;
import com.blackmirror.dongda.ui.activity.enrol.EnrolConfirmActivity;
import com.blackmirror.dongda.ui.activity.enrol.EnrolContract;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by xcx on 2018/5/9.
 */

@Component(modules = ApplyAndEnrolModule.class)
public interface EnrolConfirmComponent {

    EnrolPresenter getEnrolPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(EnrolConfirmActivity activity);
        @BindsInstance
        Builder view(EnrolContract.View view);
        EnrolConfirmComponent build();

    }
}
