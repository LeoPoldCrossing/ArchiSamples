package com.example.archimvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.archimvp.R;
import com.example.archimvp.presenter.MainPresenter;
import com.example.archimvp.utils.ActivityUtils;
import com.example.archimvp.viewmodel.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        setSupportActionBar(toolbar);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.contentFrame);
        }

        MainPresenter mainPresenter = new MainPresenter();
        mainPresenter.attachView(mainFragment);

        MainViewModel mainViewModel = new MainViewModel(this, mainPresenter);

        mainFragment.setViewModel(mainViewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
