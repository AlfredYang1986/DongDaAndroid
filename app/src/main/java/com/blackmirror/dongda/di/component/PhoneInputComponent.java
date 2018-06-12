package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.LoginModule;
import com.blackmirror.dongda.presenter.PhoneLoginPresenter;
import com.blackmirror.dongda.ui.PhoneLoginContract;
import com.blackmirror.dongda.ui.activity.landing.PhoneInputActivity;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = LoginModule.class)
public interface PhoneInputComponent {

    PhoneLoginPresenter getPhoneLoginPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(PhoneInputActivity activity);
        @BindsInstance
        Builder view(PhoneLoginContract.View view);

        PhoneInputComponent build();
    }

}
