package com.example.archimvp.di.component;

import android.app.Activity;

import com.example.archimvp.di.module.ActivityModule;
import com.example.archimvp.di.scope.ActivityScope;
import com.example.archimvp.view.MainActivity;
import com.example.archimvp.view.RepositoryActivity;

import dagger.Component;

/**
 * Created by LeoPoldCrossing on 2017/4/10.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(RepositoryActivity repositoryActivity);

    void inject(MainActivity mainActivity);


}
