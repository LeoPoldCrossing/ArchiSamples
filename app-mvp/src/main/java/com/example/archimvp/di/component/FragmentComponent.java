package com.example.archimvp.di.component;

import android.app.Activity;

import com.example.archimvp.di.module.FragmentModule;
import com.example.archimvp.di.scope.FragmentScope;
import com.example.archimvp.view.MainFragment;

import dagger.Component;

/**
 * Created by LeoPoldCrossing on 2017/4/10.
 */

@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(MainFragment mainFragment);
}
