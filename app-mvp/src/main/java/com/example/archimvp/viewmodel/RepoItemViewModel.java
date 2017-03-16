package com.example.archimvp.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.example.archimvp.R;
import com.example.archimvp.model.Repository;
import com.example.archimvp.view.RepositoryActivity;

/**
 * Created by LeoPoldCrossing on 2017/3/17.
 */

public class RepoItemViewModel extends BaseObservable {
    private Repository mRepository;
    private Context mContext;

    public RepoItemViewModel(Context context, Repository repository) {
        mContext = context;
        mRepository = repository;
    }

    public String getName() {
        return mRepository.name;
    }

    public String getDescription() {
        return mRepository.description;
    }

    public String getStars() {
        return mContext.getString(R.string.text_stars, mRepository.stars);
    }

    public String getWatchers() {
        return mContext.getString(R.string.text_watchers, mRepository.watchers);
    }

    public String getForks() {
        return mContext.getString(R.string.text_forks, mRepository.forks);
    }

    public void setRepository(Repository repository) {
        mRepository = repository;
        notifyChange();
    }

    public void onItemClick(View view) {
        RepositoryActivity.startActivity(view.getContext(), mRepository);
    }
}
