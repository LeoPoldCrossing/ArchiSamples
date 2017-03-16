package com.example.archimvp.presenter;

import com.example.archimvp.ArchiApplication;
import com.example.archimvp.model.GitHubService;
import com.example.archimvp.model.Repository;
import com.example.archimvp.model.User;
import com.example.archimvp.presenter.contract.RepositoryContract;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class RepositoryPresenter implements RepositoryContract.Presenter {
    private RepositoryContract.View mView;
    private Subscription mSubscription;

    public RepositoryPresenter(){
    }

    @Override
    public void attachView(RepositoryContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void loadUserInfo(String userUrl) {
        ArchiApplication application = ArchiApplication.getApplication(mView.getContext());
        GitHubService gitHubService = application.getGitHubService();
        mSubscription = gitHubService.userFromUrl(userUrl)
                .subscribeOn(application.getDefaultSubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mView.showUserInfo(user);
                    }
                });
    }
}
