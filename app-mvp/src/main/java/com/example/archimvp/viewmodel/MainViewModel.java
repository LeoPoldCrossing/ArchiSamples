package com.example.archimvp.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.archimvp.R;
import com.example.archimvp.presenter.MainPresenter;
import com.example.archimvp.presenter.contract.MainContract;

/**
 * Created by LeoPoldCrossing on 2017/3/16.
 */

public class MainViewModel {
    public static final String TAG = "MainViewModel";

    private MainContract.Presenter mPresenter;

    public ObservableInt progressVisibility;
    public ObservableInt infoMsgVisbility;
    public ObservableInt searchButtonVisbility;
    public ObservableInt recyclerViewVisbility;
    public ObservableField<String> infoMsg;

    private String editTextUsername;


    public MainViewModel(Context context, MainPresenter presenter) {
        mPresenter = presenter;
        progressVisibility = new ObservableInt(View.INVISIBLE);
        infoMsgVisbility = new ObservableInt(View.VISIBLE);
        searchButtonVisbility = new ObservableInt(View.GONE);
        recyclerViewVisbility = new ObservableInt(View.INVISIBLE);
        infoMsg = new ObservableField<String>(context.getString(R.string.default_info_message));
    }

    public void showProgress() {
        progressVisibility.set(View.VISIBLE);
        infoMsgVisbility.set(View.INVISIBLE);
        recyclerViewVisbility.set(View.INVISIBLE);
    }

    public void showRecylerView() {
        progressVisibility.set(View.INVISIBLE);
        infoMsgVisbility.set(View.INVISIBLE);
        recyclerViewVisbility.set(View.VISIBLE);
    }

    public void showMessage(String msg){
        progressVisibility.set(View.INVISIBLE);
        infoMsgVisbility.set(View.VISIBLE);
        recyclerViewVisbility.set(View.INVISIBLE);
        infoMsg.set(msg);
    }

    public boolean onSearchAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String username = view.getText().toString();
            if (username.length() > 0) {
                // 获取数据
                showProgress();
                mPresenter.loadGithubRepos(username);
            }
            return true;
        }
        return false;
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchButtonVisbility.set(s.length()> 0? View.VISIBLE:View.GONE);
        editTextUsername = s.toString();
    }

    public void onClickSearch(View view) {
        if(TextUtils.isEmpty(editTextUsername)){
            return;
        }
        showProgress();
        // 获取数据
        mPresenter.loadGithubRepos(editTextUsername);
    }
}
