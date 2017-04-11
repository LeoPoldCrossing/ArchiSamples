package com.example.archimvp.presenter;

import android.util.Log;

import com.example.archimvp.R;
import com.example.archimvp.base.RxPresenter;
import com.example.archimvp.common.CommonSubscriber;
import com.example.archimvp.common.RxBus;
import com.example.archimvp.model.Repository;
import com.example.archimvp.model.RetrofitHelper;
import com.example.archimvp.presenter.contract.MainContract;
import com.example.archimvp.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private List<Repository> mList;
    private RetrofitHelper retrofitHelper;

    @Inject
    public MainPresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
        registerEvent();
    }

    private void registerEvent() {
        Subscription subscription = RxBus.getDefault().tObservable(String.class)
                .compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadGithubRepos(s);
                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void loadGithubRepos(String usernameEntered) {
        String username = usernameEntered.trim();
        if (username.isEmpty()) {
            return;
        }
        mView.showProgressIndicator();
        Subscription subscription = retrofitHelper.getRepositories(username)
                .compose(RxUtil.<List<Repository>>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<List<Repository>>(mView) {
                    @Override
                    public void onCompleted() {
                        if (mList.isEmpty()) {
                            mView.showMessage(R.string.text_empty_repos);
                        } else {
                            mView.showRepostories(mList);
                        }
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        MainPresenter.this.mList = repositories;
                    }
                });
        addSubscribe(subscription);
    }
}
