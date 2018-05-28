package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.LoginModule;
import com.blackmirror.dongda.presenter.WeChatLoginPresenter;
import com.blackmirror.dongda.ui.WeChatLoginContract;
import com.blackmirror.dongda.ui.activity.SplashActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Create By Ruge at 2018-05-09
 */
@Component(modules = LoginModule.class)
public interface SplashComponent {
    WeChatLoginPresenter getWeChatLoginPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(SplashActivity activity);
        @BindsInstance
        Builder view(WeChatLoginContract.View view);
        SplashComponent build();
    }
}
