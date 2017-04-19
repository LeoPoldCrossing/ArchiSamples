package com.example.archimvp.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.archimvp.R;
import com.example.archimvp.adapter.WechatAdapter;
import com.example.archimvp.base.BaseFragment;
import com.example.archimvp.model.WxItemBean;
import com.example.archimvp.presenter.MainPresenter;
import com.example.archimvp.presenter.contract.MainContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View {


    @BindView(R.id.rv_content)
    RecyclerView rvWechatList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.iv_progress)
    ProgressImageView ivProgress;

    WechatAdapter mAdapter;
    List<WxItemBean> mList;

    boolean isLoadingMore = false;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initInject() {
//        mPresenter = new MainPresenter();
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        mList = new ArrayList<>();
        mAdapter = new WechatAdapter(mContext,mList);
        rvWechatList.setLayoutManager(new LinearLayoutManager(mContext));
        rvWechatList.setAdapter(mAdapter);
        rvWechatList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) rvWechatList.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = rvWechatList.getLayoutManager().getItemCount();
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {  //还剩2个Item时加载更多
                    if(!isLoadingMore){
                        isLoadingMore = true;
                        mPresenter.getMoreWechatData();
                    }
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getWechatData();
            }
        });
        ivProgress.start();
        mPresenter.getWechatData();
    }

    @Override
    public void showContent(List<WxItemBean> list) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<WxItemBean> list) {
        ivProgress.stop();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
        isLoadingMore = false;
    }


    @Override
    public void showErrorMsg(String msg) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
