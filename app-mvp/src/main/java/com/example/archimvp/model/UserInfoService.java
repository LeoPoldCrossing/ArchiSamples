package com.example.archimvp.model;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

public interface UserInfoService {
    String HOST = "https://api.github.com/";

    @GET
    Observable<User> userFromUrl(@Url String userUrl);
}
