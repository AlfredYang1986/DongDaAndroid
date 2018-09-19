package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.LoginModule;
import com.blackmirror.dongda.presenter.WeChatLoginPresenter;
import com.blackmirror.dongda.ui.WeChatLoginContract;
import com.blackmirror.dongda.ui.activity.landing.LandingActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Create By Ruge at 2018-05-09
 */
@Component(modules = LoginModule.class)
public interface LandingComponent {
    WeChatLoginPresenter getWeChatLoginPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(LandingActivity activity);
        @BindsInstance
        Builder view(WeChatLoginContract.View view);
        LandingComponent build();
    }
}
