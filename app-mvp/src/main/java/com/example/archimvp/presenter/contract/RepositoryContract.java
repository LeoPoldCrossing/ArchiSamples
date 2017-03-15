package com.example.archimvp.presenter.contract;

import com.example.archimvp.base.BasePresenter;
import com.example.archimvp.base.BaseView;
import com.example.archimvp.model.Repository;
import com.example.archimvp.model.User;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class RepositoryContract {
    public interface View extends BaseView {
        void showRepositoryInfo(Repository repository);

        void showUserInfo(User user);
    }

    public interface Presenter extends BasePresenter<View>{
        void bindRepositoryInfo(Repository repository);

        void loadUserInfo(String userInfoUrl);
    }
}
