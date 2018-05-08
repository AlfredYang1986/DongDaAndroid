package com.blackmirror.dongda.di.module;

import android.content.Context;

import com.blackmirror.dongda.utils.AYApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final AYApplication application;

    public AppModule(AYApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    AYApplication providerApplication(){
        return application;
    }

    @Provides
    @Singleton
    Context providerAppContext(){
        return application.getApplicationContext();
    }

}
