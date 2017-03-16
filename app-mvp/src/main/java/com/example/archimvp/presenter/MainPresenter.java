package com.example.archimvp.presenter;

import com.example.archimvp.ArchiApplication;
import com.example.archimvp.R;
import com.example.archimvp.model.GitHubService;
import com.example.archimvp.model.Repository;
import com.example.archimvp.presenter.contract.MainContract;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private Subscription subscription;
    private List<Repository> mList;


    public MainPresenter(){

    }


    @Override
    public void attachView(MainContract.View view) {
        this.mView = view;
        view.setPresenter(this);
    }

    @Override
    public void detachView() {
        this.mView = null;
        if(subscription != null ) subscription.unsubscribe();
    }

    @Override
    public void loadGithubRepos(String usernameEntered) {
        String username = usernameEntered.trim();
        if(username.isEmpty()){
            return;
        }
        if (subscription != null) {
            subscription.unsubscribe();
        }

        ArchiApplication application = ArchiApplication.getApplication(mView.getContext());
        GitHubService gitHubService = application.getGitHubService();
        subscription = gitHubService.publicRepositories(username)
                .subscribeOn(application.getDefaultSubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        if(mList.isEmpty()){
                            mView.showMessage(R.string.text_empty_repos);
                        }else{
                            mView.showRepostories(mList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isHttp404(e)) {
                            mView.showMessage(R.string.error_username_not_found);
                        } else {
                            mView.showMessage(R.string.error_loading_repos);
                        }
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        MainPresenter.this.mList = repositories;
                    }
                });

    }
    private static boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }

}
