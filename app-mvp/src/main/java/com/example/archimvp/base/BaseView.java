package com.example.archimvp.base;


import android.content.Context;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public interface BaseView<T> {
    Context getContext();

    void setPresenter(T presenter);
}
