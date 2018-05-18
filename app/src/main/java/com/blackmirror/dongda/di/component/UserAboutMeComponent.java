package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.UserInfoModule;
import com.blackmirror.dongda.presenter.UserInfoPresenter;
import com.blackmirror.dongda.ui.activity.UserAboutMeActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by xcx on 2018/5/9.
 */

@Component(modules = UserInfoModule.class)
public interface UserAboutMeComponent {

    UserInfoPresenter getUserInfoPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(UserAboutMeActivity activity);
        UserAboutMeComponent build();

    }
}
