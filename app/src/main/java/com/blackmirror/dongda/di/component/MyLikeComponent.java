package com.blackmirror.dongda.di.component;

import com.blackmirror.dongda.di.module.CommonModule;
import com.blackmirror.dongda.presenter.MyLikePresenter;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.ui.activity.MyLikeActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Create By Ruge at 2018-05-11
 */
@Component(modules = CommonModule.class)
public interface MyLikeComponent {

    MyLikePresenter getMyLikePresenter();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder activity(MyLikeActivity activity);
        @BindsInstance
        Builder view(Contract.MyLikeView view);
        MyLikeComponent build();
    }
}
