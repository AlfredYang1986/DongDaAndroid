package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.LoginModule;
import com.blackmirror.dongda.ui.activity.Landing.LandingActivity;

import dagger.Component;

@Component(modules = {LoginModule.class})
public interface LoginComponent {
    void injectLandingActivity(LandingActivity activity);
}
