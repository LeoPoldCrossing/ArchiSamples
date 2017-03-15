package com.example.archimvp.base;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
