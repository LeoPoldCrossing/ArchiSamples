package com.example.archimvp.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.archimvp.R;
import com.example.archimvp.adapter.RepositoryAdapter;
import com.example.archimvp.base.BaseFragment;
import com.example.archimvp.common.RxBus;
import com.example.archimvp.model.Repository;
import com.example.archimvp.presenter.MainPresenter;
import com.example.archimvp.presenter.contract.MainContract;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LeoPoldCrossing on 2017/3/14.
 */

public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View {

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
        setupRecyclerView(reposRecyclerView);
        editTextUsername.addTextChangedListener(mHideShowButtonTextWatcher);
        // 该监听器在点击键盘的回车键时触发
        editTextUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String username = editTextUsername.getText().toString();
                    if (username.length() > 0)
                        RxBus.getDefault().post(username);
                    return true;
                }
                return false;
            }
        });
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
        RxBus.getDefault().post(editTextUsername.getText().toString());
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
    }

}
