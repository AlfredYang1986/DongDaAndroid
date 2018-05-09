package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.LoginModule;
import com.blackmirror.dongda.ui.UpdateUserInfoContract;
import com.blackmirror.dongda.ui.activity.Landing.NameInputActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by xcx on 2018/5/9.
 */

@Component(modules = LoginModule.class)
public interface NameInputComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(NameInputActivity activity);
        @BindsInstance
        Builder view(UpdateUserInfoContract.View view);
        NameInputComponent build();

    }
}
