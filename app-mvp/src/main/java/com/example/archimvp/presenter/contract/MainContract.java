package com.example.archimvp.presenter.contract;

import com.example.archimvp.base.BasePresenter;
import com.example.archimvp.base.BaseView;
import com.example.archimvp.model.Repository;
import com.example.archimvp.model.WxItemBean;

import java.util.List;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class MainContract {
    public interface View extends BaseView {

        void showContent(List<WxItemBean> mList);

        void showMoreContent(List<WxItemBean> mList);
    }

    public interface Presenter extends BasePresenter<View> {

        void getWechatData();

        void getMoreWechatData();
    }
}
