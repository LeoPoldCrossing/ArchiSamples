package com.example.archimvp;

import android.app.Application;
import android.content.Context;

import com.example.archimvp.model.GitHubService;
import com.example.archimvp.model.RetrofitHelper;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

public class ArchiApplication extends Application {

    private GitHubService gitHubService;
    private Scheduler defaultSubscribeScheduler;

    public Scheduler getDefaultSubscribeScheduler() {
        if(defaultSubscribeScheduler == null){
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    public void setDefaultSubscribeScheduler(Scheduler defaultSubscribeScheduler) {
        this.defaultSubscribeScheduler = defaultSubscribeScheduler;
    }

    public static ArchiApplication getApplication(Context context){
        return (ArchiApplication) context.getApplicationContext();
    }

    public GitHubService getGitHubService(){
        if (gitHubService == null) {
            gitHubService = RetrofitHelper.create();
        }
        return gitHubService;
    }

    public void setGitHubService(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }


}
