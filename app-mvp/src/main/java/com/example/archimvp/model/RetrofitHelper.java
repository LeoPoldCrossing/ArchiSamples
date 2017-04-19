package com.example.archimvp.model;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

//todo 省略这一层
public class RetrofitHelper {
    private RepositoriesService repositoriesService;
    private UserInfoService userInfoService;

    public RetrofitHelper(RepositoriesService repositoriesService, UserInfoService userInfoService){
        this.repositoriesService = repositoriesService;
        this.userInfoService = userInfoService;
    }


    public Observable<HttpResponse<List<WxItemBean>>> fetchWechatListInfo(int num, int page) {
        return repositoriesService.getWXHot("52b7ec3471ac3bec6846577e79f20e4c", num, page);
    }

    public Observable<User> getUserInfo(String url){
        return userInfoService.userFromUrl(url);
    }
}
