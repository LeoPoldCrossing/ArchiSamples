package com.example.archimvp.di.module;

import com.example.archimvp.ArchiApplication;
import com.example.archimvp.model.RepositoriesService;
import com.example.archimvp.model.RetrofitHelper;
import com.example.archimvp.model.UserInfoService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LeoPoldCrossing on 2017/4/8.
 */

@Module
public class AppModule {
    private final ArchiApplication application;

    public AppModule(ArchiApplication application){
        this.application = application;
    }

    @Provides
    @Singleton
    ArchiApplication provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper(RepositoriesService repositoriesService, UserInfoService userInfoService){
        return new RetrofitHelper(repositoriesService, userInfoService);
    }

}
