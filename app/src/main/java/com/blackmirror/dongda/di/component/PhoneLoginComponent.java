package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.LoginModule;
import com.blackmirror.dongda.ui.activity.Landing.PhoneInputActivity;

import dagger.Component;

@Component(modules = LoginModule.class)
public interface PhoneLoginComponent {
    void injectPhoneInputActivity(PhoneInputActivity activity);
}
