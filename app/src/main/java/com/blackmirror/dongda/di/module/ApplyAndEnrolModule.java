package com.blackmirror.dongda.di.module;

import com.blackmirror.dongda.data.repository.ApplyAndEnrolRepositoryImpl;
import com.blackmirror.dongda.domain.Interactor.ApplyServiceUseCase;
import com.blackmirror.dongda.domain.Interactor.enrol.BrandAllLocUseCase;
import com.blackmirror.dongda.domain.Interactor.enrol.LocAllServiceUseCase;
import com.blackmirror.dongda.domain.repository.ApplyAndEnrolRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplyAndEnrolModule {

    @Provides
    ApplyAndEnrolRepository provideApplyAndEnrolRepository(){
        return new ApplyAndEnrolRepositoryImpl();
    }

    @Provides
    ApplyServiceUseCase provideApplyServiceUseCase(ApplyAndEnrolRepository repository){
        return new ApplyServiceUseCase(repository);
    }

    @Provides
    BrandAllLocUseCase provideBrandAllLocUseCase(ApplyAndEnrolRepository repository){
        return new BrandAllLocUseCase(repository);
    }

    @Provides
    LocAllServiceUseCase provideLocAllServiceUseCase(ApplyAndEnrolRepository repository){
        return new LocAllServiceUseCase(repository);
    }

}
