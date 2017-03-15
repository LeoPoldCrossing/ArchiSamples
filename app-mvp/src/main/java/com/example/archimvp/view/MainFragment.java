package com.example.archimvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.archimvp.R;
import com.example.archimvp.adapter.RepositoryAdapter;
import com.example.archimvp.model.Repository;
import com.example.archimvp.presenter.contract.MainContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class MainFragment extends Fragment implements MainContract.View {

    @BindView(R.id.button_search)
    ImageButton buttonSearch;
    @BindView(R.id.edit_text_username)
    EditText editTextUsername;
    @BindView(R.id.layout_search)
    RelativeLayout layoutSearch;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.text_info)
    TextView textInfo;
    @BindView(R.id.repos_recycler_view)
    RecyclerView reposRecyclerView;

    private MainContract.Presenter mPresenter;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, inflate);
        injectView();
        return inflate;
    }

    private void injectView() {
        setupRecyclerView(reposRecyclerView);

        editTextUsername.addTextChangedListener(mHideShowButtonTextWatcher);

        // 该监听器在点击键盘的回车键时触发
        editTextUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String username = editTextUsername.getText().toString();
                    if (username.length() > 0) mPresenter.loadGithubRepos(username);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    private void setupRecyclerView(RecyclerView reposRecyclerView) {
        RepositoryAdapter adapter = new RepositoryAdapter();
        adapter.setOnItemClickListener(new RepositoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Repository repository) {
                // 跳转到详情页
                RepositoryActivity.startActivity(MainFragment.this.getContext(), repository);
            }
        });
        reposRecyclerView.setAdapter(adapter);
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private TextWatcher mHideShowButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            buttonSearch.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void showProgressIndicator() {
        progress.setVisibility(View.VISIBLE);
        textInfo.setVisibility(View.INVISIBLE);
        reposRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
    }

    @Override
    public void showRepostories(List<Repository> repositories) {
        RepositoryAdapter adapter = (RepositoryAdapter) reposRecyclerView.getAdapter();
        adapter.setRepositories(repositories);
        reposRecyclerView.requestFocus();
        hideSoftKeyboard();
        progress.setVisibility(View.INVISIBLE);
        textInfo.setVisibility(View.INVISIBLE);
        reposRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(int resId) {
        progress.setVisibility(View.INVISIBLE);
        textInfo.setVisibility(View.VISIBLE);
        reposRecyclerView.setVisibility(View.INVISIBLE);
        textInfo.setText(this.getContext().getString(resId));
    }

    @OnClick(R.id.button_search)
    public void searchClick() {
        mPresenter.loadGithubRepos(editTextUsername.getText().toString());
    }
}
