package com.example.archimvp.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.archimvp.R;
import com.example.archimvp.base.SimpleActivity;
import com.example.archimvp.utils.ActivityUtils;

import butterknife.BindView;


public class MainActivity extends SimpleActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        setSupportActionBar(toolbar);
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.contentFrame);
        }
    }

}
