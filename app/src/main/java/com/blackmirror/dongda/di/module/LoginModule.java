package com.blackmirror.dongda.di.module;

import com.blackmirror.dongda.data.repository.LoginRepositoryImpl;
import com.blackmirror.dongda.di.scope.PerActivity;
import com.blackmirror.dongda.domain.Interactor.PhoneLoginUserCase;
import com.blackmirror.dongda.domain.Interactor.SendSmsUserCase;
import com.blackmirror.dongda.domain.Interactor.WeChatLoginUserCase;
import com.blackmirror.dongda.domain.repository.LoginRepository;

import dagger.Module;
import dagger.Provides;

@Module
@PerActivity
public class LoginModule {

    @Provides
    LoginRepository provideLoginRepository(){
        return new LoginRepositoryImpl();
    }

    @Provides
    SendSmsUserCase providerSendSmsUserCase(LoginRepository repository){
        return new SendSmsUserCase(repository);
    }

    @Provides
    PhoneLoginUserCase providerPhoneLoginUserCase(LoginRepository repository){
        return new PhoneLoginUserCase(repository);
    }

    @Provides
    WeChatLoginUserCase providerWeChatLoginUserCase(LoginRepository repository){
        return new WeChatLoginUserCase(repository);
    }
}
