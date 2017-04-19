package com.example.archimvp.presenter;

import com.example.archimvp.base.RxPresenter;
import com.example.archimvp.common.CommonSubscriber;
import com.example.archimvp.model.HttpResponse;
import com.example.archimvp.model.RetrofitHelper;
import com.example.archimvp.model.WxItemBean;
import com.example.archimvp.presenter.contract.MainContract;
import com.example.archimvp.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public MainPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getWechatData() {
        currentPage = 1;
        Subscription rxSubscription = mRetrofitHelper.fetchWechatListInfo(NUM_OF_PAGE, currentPage)
                .compose(RxUtil.<HttpResponse<List<WxItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WxItemBean>>handleResult())
                .subscribe(new CommonSubscriber<List<WxItemBean>>(mView) {
                    @Override
                    public void onNext(List<WxItemBean> wxItemBeen) {
                        mView.showContent(wxItemBeen);
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void getMoreWechatData() {
        Observable<HttpResponse<List<WxItemBean>>> observable;
        observable = mRetrofitHelper.fetchWechatListInfo(NUM_OF_PAGE, ++currentPage);
        Subscription rxSubscription = observable
                .compose(RxUtil.<HttpResponse<List<WxItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WxItemBean>>handleResult())
                .subscribe(new CommonSubscriber<List<WxItemBean>>(mView, "没有更多了ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(List<WxItemBean> wxItemBeen) {
                        mView.showMoreContent(wxItemBeen);
                    }
                });
        addSubscribe(rxSubscription);
    }
}
