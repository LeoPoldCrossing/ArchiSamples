package com.example.archimvp.model;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

public class RetrofitHelper {

    private RetrofitHelper(){
    }

    private static class RetrofitHelperHolder{
        private static final RetrofitHelper sInstance = new RetrofitHelper();
    }

    public static RetrofitHelper shareInstance(){
        return RetrofitHelperHolder.sInstance;
    }

    public  GitHubService createGithubService(){
        return createRetrofit("https://api.github.com/").create(GitHubService.class);
    }

    private Retrofit createRetrofit(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
