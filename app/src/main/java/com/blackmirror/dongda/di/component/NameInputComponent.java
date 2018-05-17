package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.LoginModule;
import com.blackmirror.dongda.presenter.UserInfoPresenter;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.ui.activity.Landing.NameInputActivity;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by xcx on 2018/5/9.
 */

@Component(modules = LoginModule.class)
public interface NameInputComponent {

    @Named("NameInput")
    UserInfoPresenter getUserInfoPresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(NameInputActivity activity);
        @BindsInstance
        Builder view(Contract.NameInputView view);
        NameInputComponent build();

    }
}
