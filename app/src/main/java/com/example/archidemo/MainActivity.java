package com.example.archidemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.archidemo.model.GitHubService;
import com.example.archidemo.model.Repository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Subscription subscription;
    @BindView(R.id.repos_recycler_view)
    RecyclerView reposRecycleView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_text_username)
    EditText editTextUsername;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.text_info)
    TextView infoTextView;
    @BindView(R.id.button_search)
    ImageButton searchButton;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(MainActivity.this);

        setSupportActionBar(toolbar);
        setupRecyclerView(reposRecycleView);

        editTextUsername.addTextChangedListener(mHideShowButtonTextWatcher);

        // 该监听器在点击键盘的回车键时触发
        editTextUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String username = editTextUsername.getText().toString();
                    if (username.length() > 0) loadGithubRepos(username);
                    return true;
                }
                return false;
            }
        });
    }

    private void setupRecyclerView(RecyclerView reposRecycleView) {
        RepositoryAdapter adapter = new RepositoryAdapter();
        adapter.setOnItemClickListener(new RepositoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Repository repository) {
                // 跳转到详情页
                Log.e(TAG, repository.toString());
                RepositoryActivity.startActivity(MainActivity.this, repository);
            }
        });
        reposRecycleView.setAdapter(adapter);
        reposRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadGithubRepos(String username) {
        progressBar.setVisibility(View.VISIBLE);
        reposRecycleView.setVisibility(View.GONE);
        infoTextView.setVisibility(View.GONE);

        ArchiApplication application = ArchiApplication.getApplication(this);
        GitHubService gitHubService = application.getGitHubService();
        subscription = gitHubService.publicRepositories(username)
                .subscribeOn(application.getDefaultSubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        if (reposRecycleView.getAdapter().getItemCount() > 0) {
                            reposRecycleView.requestFocus();
                            hideSoftKeyboard();
                            reposRecycleView.setVisibility(View.VISIBLE);
                        } else {
                            infoTextView.setText(R.string.text_empty_repos);
                            infoTextView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading GitHub repos ", e);
                        progressBar.setVisibility(View.GONE);
                        if (e instanceof HttpException
                                && ((HttpException) e).code() == 404) {
                            infoTextView.setText(R.string.error_username_not_found);
                        } else {
                            infoTextView.setText(R.string.error_loading_repos);
                        }
                        infoTextView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        RepositoryAdapter adapter = (RepositoryAdapter) reposRecycleView.getAdapter();
                        adapter.setRepositories(repositories);
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
        }
        unbinder.unbind();
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
    }


    @OnClick(R.id.button_search)
    public void searchClick() {
        loadGithubRepos(editTextUsername.getText().toString());
    }

    private TextWatcher mHideShowButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchButton.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
