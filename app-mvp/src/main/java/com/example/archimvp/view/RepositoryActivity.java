package com.example.archimvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.archimvp.R;
import com.example.archimvp.model.Repository;
import com.example.archimvp.model.User;
import com.example.archimvp.presenter.RepositoryPresenter;
import com.example.archimvp.presenter.contract.RepositoryContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RepositoryActivity extends AppCompatActivity implements RepositoryContract.View{

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";
    private static final String TAG = "RepositoryActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_repo_description)
    TextView textRepoDescription;
    @BindView(R.id.layout_header)
    LinearLayout layoutHeader;
    @BindView(R.id.text_homepage)
    TextView textHomepage;
    @BindView(R.id.text_language)
    TextView textLanguage;
    @BindView(R.id.text_fork)
    TextView textFork;
    @BindView(R.id.image_owner)
    CircleImageView imageOwner;
    @BindView(R.id.text_owner_name)
    TextView textOwnerName;
    @BindView(R.id.text_owner_email)
    TextView textOwnerEmail;
    @BindView(R.id.text_owner_location)
    TextView textOwnerLocation;
    @BindView(R.id.layout_owner)
    RelativeLayout layoutOwner;

    private RepositoryContract.Presenter mPresenter;
    public static void startActivity(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (mPresenter == null) {
            mPresenter = new RepositoryPresenter();
        }
        mPresenter.attachView(this);
        Repository repository = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
        mPresenter.bindRepositoryInfo(repository);
        mPresenter.loadUserInfo(repository.owner.url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }


    @Override
    public void showRepositoryInfo(Repository repository) {
        setTitle(repository.name);
        textRepoDescription.setText(repository.description);
        textHomepage.setText(repository.homepage);
        textHomepage.setVisibility(repository.hasHomepage() ? View.VISIBLE : View.GONE);
        textLanguage.setText(getString(R.string.text_language, repository.language));
        textLanguage.setVisibility(repository.hasLanguage() ? View.VISIBLE : View.GONE);
        textFork.setVisibility(repository.isFork() ? View.VISIBLE : View.GONE);

    }

    @Override
    public void showUserInfo(User user) {
        if (user == null) {
            return;
        }
        layoutOwner.setVisibility(View.VISIBLE);
        textOwnerName.setText(user.name);
        textOwnerEmail.setText(user.email);
        textOwnerEmail.setVisibility(user.hasEmail() ? View.VISIBLE : View.GONE);
        textOwnerLocation.setText(user.location);
        textOwnerLocation.setVisibility(user.hasLocation() ? View.VISIBLE : View.GONE);
        //Preload image for user because we already have it before loading the full user
        Picasso.with(this)
                .load(user.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .into(imageOwner);
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
