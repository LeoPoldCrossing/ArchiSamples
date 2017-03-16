package com.example.archimvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.archimvp.adapter.RepositoryAdapter;
import com.example.archimvp.databinding.FragmentMainBinding;
import com.example.archimvp.model.Repository;
import com.example.archimvp.presenter.contract.MainContract;
import com.example.archimvp.viewmodel.MainViewModel;

import java.util.List;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class MainFragment extends Fragment implements MainContract.View {

    private MainContract.Presenter mPresenter;

    private MainViewModel mainViewModel;

    private FragmentMainBinding fragmentMainBinding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setViewModel(MainViewModel viewModel) {
        mainViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false);
        fragmentMainBinding.setViewModel(mainViewModel);
        setupRecyclerView(fragmentMainBinding.reposRecyclerView);
        return fragmentMainBinding.getRoot();
    }

    private void setupRecyclerView(RecyclerView reposRecyclerView) {
        RepositoryAdapter adapter = new RepositoryAdapter();
        reposRecyclerView.setAdapter(adapter);
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fragmentMainBinding.reposRecyclerView.getWindowToken(), 0);
    }

    @Override
    public void showProgressIndicator() {
        mainViewModel.showProgress();
    }

    @Override
    public void showRepostories(List<Repository> repositories) {
        mainViewModel.showRecylerView();
        RepositoryAdapter adapter = (RepositoryAdapter) fragmentMainBinding.reposRecyclerView.getAdapter();
        adapter.setRepositories(repositories);
        hideSoftKeyboard();
    }

    @Override
    public void showMessage(int resId) {
        mainViewModel.showMessage(getContext().getString(resId));
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

}
