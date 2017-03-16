package com.example.archimvp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archimvp.R;
import com.example.archimvp.databinding.ItemRepoBinding;
import com.example.archimvp.model.Repository;
import com.example.archimvp.viewmodel.RepoItemViewModel;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private List<Repository> mList;

    public RepositoryAdapter() {
        mList = Collections.emptyList();
    }

    public RepositoryAdapter(List<Repository> repositories) {
        this.mList = repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.mList = repositories;
        this.notifyDataSetChanged();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRepoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_repo,
                parent,
                false);
        return new RepositoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RepositoryViewHolder holder, int position) {
        holder.bindRepository(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class RepositoryViewHolder extends RecyclerView.ViewHolder {
        final ItemRepoBinding binding;

        public RepositoryViewHolder(ItemRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindRepository(Repository repository) {
            if (binding.getItemViewModel() == null) {
                binding.setItemViewModel(new RepoItemViewModel(itemView.getContext(), repository));
            } else {
                binding.getItemViewModel().setRepository(repository);
            }
        }
    }
}


