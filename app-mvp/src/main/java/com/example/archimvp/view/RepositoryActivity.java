package com.example.archimvp.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.archimvp.R;
import com.example.archimvp.databinding.ActivityRepositoryBinding;
import com.example.archimvp.model.Repository;
import com.example.archimvp.model.User;
import com.example.archimvp.presenter.RepositoryPresenter;
import com.example.archimvp.presenter.contract.RepositoryContract;
import com.example.archimvp.viewmodel.RepositoryViewModel;

public class RepositoryActivity extends AppCompatActivity implements RepositoryContract.View{

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";
    private static final String TAG = "RepositoryActivity";

    private RepositoryContract.Presenter mPresenter;
    private ActivityRepositoryBinding binding;
    private RepositoryViewModel viewModel;

    public static void startActivity(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository);
        setSupportActionBar(binding.toolbar);
        if (mPresenter == null) {
            mPresenter = new RepositoryPresenter();
        }
        mPresenter.attachView(this);
        Repository repository = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
        viewModel = new RepositoryViewModel(this, repository);
        binding.setRepositoryViewModel(viewModel);
        mPresenter.loadUserInfo(repository.owner.url);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    public void showUserInfo(User user) {
        if (user == null) {
            return;
        }
        viewModel.setUser(user);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setPresenter(RepositoryContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
