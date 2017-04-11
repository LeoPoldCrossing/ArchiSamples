package com.example.archimvp.di.module;

import com.example.archimvp.di.qualifier.RespositoriesUrl;
import com.example.archimvp.di.qualifier.UserInfoUrl;
import com.example.archimvp.model.RepositoriesService;
import com.example.archimvp.model.UserInfoService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LeoPoldCrossing on 2017/4/11.
 */

@Module
public class HttpModule {

    /**
     * 提供builder
     * */
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder (){
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder();
    }



    /**
     * 提供客户端
     * */
    @Singleton
    @Provides
    @RespositoriesUrl
    Retrofit provideRepositoriesRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder, client, RepositoriesService.HOST);
    }

    @Singleton
    @Provides
    @UserInfoUrl
    Retrofit provideUserInfoRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder, client, RepositoriesService.HOST);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        return builder.connectTimeout(60000L, TimeUnit.MILLISECONDS)
                .readTimeout(60000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60000L, TimeUnit.MILLISECONDS)
                .build();
    }


    /**
     * 提供API
     * */
    @Singleton
    @Provides
    RepositoriesService provideRepositoriesService(@RespositoriesUrl Retrofit retrofit){
        return retrofit.create(RepositoriesService.class);
    }

    @Singleton
    @Provides
    UserInfoService provideUserInfoService(@UserInfoUrl Retrofit retrofit){
        return retrofit.create(UserInfoService.class);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String host) {
        return builder
                .baseUrl(host)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
