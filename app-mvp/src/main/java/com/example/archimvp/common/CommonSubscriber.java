package com.example.archimvp.common;

import android.text.TextUtils;

import com.example.archimvp.base.BaseView;
import com.example.archimvp.model.exception.APIException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by LeoPoldCrossing on 2017/3/15.
 */

public abstract class CommonSubscriber<T> extends Subscriber<T> {

    private BaseView mView;
    private String mErrorMsg;

    protected CommonSubscriber(BaseView mView) {
        this.mView = mView;
    }

    protected CommonSubscriber(BaseView mView, String mErrorMsg) {
        this(mView);
        this.mErrorMsg = mErrorMsg;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof HttpException) {
            mView.showErrorMsg("网络异常");
        }else if(e instanceof APIException){
            mView.showErrorMsg(mErrorMsg);
        }

    }

}
