package com.example.archimvp.di.component;

import com.example.archimvp.ArchiApplication;
import com.example.archimvp.di.module.AppModule;
import com.example.archimvp.di.module.HttpModule;
import com.example.archimvp.model.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by LeoPoldCrossing on 2017/4/7.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    ArchiApplication getContext();  // 提供App的Context

    RetrofitHelper getRetrofitHelper();

}
