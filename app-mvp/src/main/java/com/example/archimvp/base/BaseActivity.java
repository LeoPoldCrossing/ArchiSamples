package com.example.archimvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.archimvp.ArchiApplication;
import com.example.archimvp.di.component.ActivityComponent;
import com.example.archimvp.di.component.DaggerActivityComponent;
import com.example.archimvp.di.module.ActivityModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by LeoPoldCrossing on 2017/3/15.
 */

public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView {

    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnbinder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initEventAndData();
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnbinder.unbind();
    }

    // 子类实现，返回layout id
    protected abstract int getLayout();

    protected abstract void initInject();

    protected abstract void initEventAndData();

    protected ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
                .appComponent(ArchiApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

}
