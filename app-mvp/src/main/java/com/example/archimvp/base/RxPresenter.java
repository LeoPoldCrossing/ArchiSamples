package com.example.archimvp.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LeoPoldCrossing on 2017/3/15.
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription){
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }


    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
