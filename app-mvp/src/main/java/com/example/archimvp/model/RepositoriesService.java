package com.example.archimvp.model;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

public interface RepositoriesService {

    String HOST = "http://api.tianapi.com/";

    /**
     * 微信精选列表
     */
    @GET("wxnew")
    Observable<HttpResponse<List<WxItemBean>>> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);
}
