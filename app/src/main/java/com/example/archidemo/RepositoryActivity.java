package com.example.archidemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.archidemo.model.GitHubService;
import com.example.archidemo.model.Repository;
import com.example.archidemo.model.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RepositoryActivity extends AppCompatActivity {

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

    private Subscription subscription;
    private Unbinder unbinder;

    public static void startActivity(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        unbinder = ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Repository repository = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
        bindRepositoryData(repository);
        loadFullUser(repository.owner.url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) subscription.unsubscribe();
        unbinder.unbind();
    }

    private void bindRepositoryData(final Repository repository) {
        setTitle(repository.name);
        textRepoDescription.setText(repository.description);
        textHomepage.setText(repository.homepage);
        textHomepage.setVisibility(repository.hasHomepage() ? View.VISIBLE : View.GONE);
        textLanguage.setText(getString(R.string.text_language, repository.language));
        textLanguage.setVisibility(repository.hasLanguage() ? View.VISIBLE : View.GONE);
        textFork.setVisibility(repository.isFork() ? View.VISIBLE : View.GONE);
        //Preload image for user because we already have it before loading the full user
        Picasso.with(this)
                .load(repository.owner.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .into(imageOwner);
    }

    private void bindOwnerData(final User owner) {
        textOwnerName.setText(owner.name);
        textOwnerEmail.setText(owner.email);
        textOwnerEmail.setVisibility(owner.hasEmail() ? View.VISIBLE : View.GONE);
        textOwnerLocation.setText(owner.location);
        textOwnerLocation.setVisibility(owner.hasLocation() ? View.VISIBLE : View.GONE);
    }


    private void loadFullUser(String url) {
        ArchiApplication application = ArchiApplication.getApplication(this);
        GitHubService githubService = application.getGitHubService();
        subscription = githubService.userFromUrl(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.getDefaultSubscribeScheduler())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Log.i(TAG, "Full user data loaded " + user);
                        bindOwnerData(user);
                        layoutOwner.setVisibility(View.VISIBLE);
                    }
                });
    }
}
