package com.blackmirror.dongda.di.module;

import com.blackmirror.dongda.data.repository.LoginRepositoryImpl;
import com.blackmirror.dongda.domain.Interactor.PhoneLoginUseCase;
import com.blackmirror.dongda.domain.Interactor.SendSmsUseCase;
import com.blackmirror.dongda.domain.Interactor.WeChatLoginUseCase;
import com.blackmirror.dongda.domain.repository.LoginRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    LoginRepository provideLoginRepository(){
        return new LoginRepositoryImpl();
    }

    @Provides
    SendSmsUseCase providerSendSmsUserCase(LoginRepository repository){
        return new SendSmsUseCase(repository);
    }

    @Provides
    PhoneLoginUseCase providerPhoneLoginUserCase(LoginRepository repository){
        return new PhoneLoginUseCase(repository);
    }

    @Provides
    WeChatLoginUseCase providerWeChatLoginUserCase(LoginRepository repository){
        return new WeChatLoginUseCase(repository);
    }
}
