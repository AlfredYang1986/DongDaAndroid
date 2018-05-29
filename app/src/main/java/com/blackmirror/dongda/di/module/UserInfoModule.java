package com.blackmirror.dongda.di.module;

import com.blackmirror.dongda.data.repository.UserInfoRepositoryImpl;
import com.blackmirror.dongda.domain.Interactor.UpdateUserInfoUseCase;
import com.blackmirror.dongda.domain.Interactor.userinfo.QueryUserInfoUseCase;
import com.blackmirror.dongda.domain.repository.UserInfoRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class UserInfoModule {

    @Provides
    UserInfoRepository provideUserInfoRepository(){
        return new UserInfoRepositoryImpl();
    }

    @Provides
    QueryUserInfoUseCase provideQueryUserInfoUseCase(UserInfoRepository repository){
        return new QueryUserInfoUseCase(repository);
    }

    @Provides
    UpdateUserInfoUseCase provideUpdateUserInfoUseCase(UserInfoRepository repository){
        return new UpdateUserInfoUseCase(repository);
    }

}
