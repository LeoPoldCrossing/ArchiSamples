package com.example.archimvp.model;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

public class RetrofitHelper {
    private RepositoriesService repositoriesService;
    private UserInfoService userInfoService;

    public RetrofitHelper(RepositoriesService repositoriesService, UserInfoService userInfoService){
        this.repositoriesService = repositoriesService;
        this.userInfoService = userInfoService;
    }

    public Observable<List<Repository>> getRepositories(String userName){
        return repositoriesService.publicRepositories(userName);
    }

    public Observable<User> getUserInfo(String url){
        return userInfoService.userFromUrl(url);
    }

    private Retrofit createRetrofit(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
