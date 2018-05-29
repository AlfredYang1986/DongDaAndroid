package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.ApplyAndEnrolModule;
import com.blackmirror.dongda.presenter.ApplyPresenter;
import com.blackmirror.dongda.ui.activity.apply.ApplyContract;
import com.blackmirror.dongda.ui.activity.apply.ApplyServiceActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by xcx on 2018/5/9.
 */

@Component(modules = ApplyAndEnrolModule.class)
public interface ApplyServiceComponent {

    ApplyPresenter getApplyPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(ApplyServiceActivity activity);
        @BindsInstance
        Builder view(ApplyContract.View view);
        ApplyServiceComponent build();

    }
}
