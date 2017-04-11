package com.example.archimvp.presenter;

import com.example.archimvp.base.RxPresenter;
import com.example.archimvp.common.CommonSubscriber;
import com.example.archimvp.model.Repository;
import com.example.archimvp.model.RetrofitHelper;
import com.example.archimvp.model.User;
import com.example.archimvp.presenter.contract.RepositoryContract;
import com.example.archimvp.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class RepositoryPresenter extends RxPresenter<RepositoryContract.View> implements RepositoryContract.Presenter {
    private User user;
    private RetrofitHelper retrofitHelper;

     @Inject
    public RepositoryPresenter(RetrofitHelper retrofitHelper){
         this.retrofitHelper = retrofitHelper;
    }


    @Override
    public void bindRepositoryInfo(Repository repository) {
        mView.showRepositoryInfo(repository);
    }

    @Override
    public void loadUserInfo(String userUrl) {
        Subscription subscription = retrofitHelper.getUserInfo(userUrl)
                .compose(RxUtil.<User>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<User>(mView) {
                    @Override
                    public void onNext(User user) {
                        RepositoryPresenter.this.user = user;
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mView.showUserInfo(user);
                    }
                });
        addSubscribe(subscription);
    }
}
