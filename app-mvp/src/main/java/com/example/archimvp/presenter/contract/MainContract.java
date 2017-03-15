package com.example.archimvp.presenter.contract;

import com.example.archimvp.base.BasePresenter;
import com.example.archimvp.base.BaseView;
import com.example.archimvp.model.Repository;

import java.util.List;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class MainContract {
    public interface View extends BaseView<Presenter>{
        void showProgressIndicator();

        void showRepostories(List<Repository> repositories);

        void showMessage(int resId);
    }

    public interface Presenter extends BasePresenter<View>{
        void loadGithubRepos(String username);
    }
}
