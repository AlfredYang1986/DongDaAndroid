package com.blackmirror.dongda.di.module;

import com.blackmirror.dongda.data.repository.CommonRepositoryImpl;
import com.blackmirror.dongda.domain.Interactor.GetMoreDataUseCase;
import com.blackmirror.dongda.domain.Interactor.HomeUseCase;
import com.blackmirror.dongda.domain.Interactor.LikePopUseCase;
import com.blackmirror.dongda.domain.Interactor.LikePushUseCase;
import com.blackmirror.dongda.domain.repository.CommonRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Create By Ruge at 2018-05-11
 */

@Module
public class CommonModule {

    @Provides
    CommonRepository provideCommonRepository(){
        return new CommonRepositoryImpl();
    }

    @Provides
    HomeUseCase provideHomeUseCase(CommonRepository repository){
        return new HomeUseCase(repository);
    }

    @Provides
    LikePushUseCase provideLikePushUseCase(CommonRepository repository){
        return new LikePushUseCase(repository);
    }

    @Provides
    LikePopUseCase provideLikePopUseCase(CommonRepository repository){
        return new LikePopUseCase(repository);
    }

    @Provides
    GetMoreDataUseCase provideGetMoreDataUseCase(CommonRepository repository){
        return new GetMoreDataUseCase(repository);
    }

}
