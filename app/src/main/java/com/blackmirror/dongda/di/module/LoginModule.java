package com.blackmirror.dongda.di.module;

import com.blackmirror.dongda.data.repository.LoginRepositoryImpl;
import com.blackmirror.dongda.domain.Interactor.PhoneLoginUseCase;
import com.blackmirror.dongda.domain.Interactor.SendSmsUseCase;
import com.blackmirror.dongda.domain.Interactor.UpLoadWeChatIconUseCase;
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
    SendSmsUseCase provideSendSmsUserCase(LoginRepository repository){
        return new SendSmsUseCase(repository);
    }

    @Provides
    PhoneLoginUseCase providePhoneLoginUserCase(LoginRepository repository){
        return new PhoneLoginUseCase(repository);
    }

    @Provides
    WeChatLoginUseCase provideWeChatLoginUserCase(LoginRepository repository){
        return new WeChatLoginUseCase(repository);
    }

    @Provides
    UpLoadWeChatIconUseCase provideUpLoadWeChatIconUseCase(LoginRepository repository){
        return new UpLoadWeChatIconUseCase(repository);
    }
}
