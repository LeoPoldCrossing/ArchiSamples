package com.example.archidemo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archidemo.model.Repository;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private List<Repository> mList;
    private OnItemClickListener onItemClickListener;

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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        return new RepositoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RepositoryViewHolder holder, int position) {
        holder.repository = mList.get(position);
        Context context = holder.textRepoTitle.getContext();

        holder.textRepoTitle.setText(holder.repository.name);
        holder.textRepoDescription.setText(holder.repository.description);

        holder.textWatchers.setText(
                context.getResources().getString(R.string.text_watchers, holder.repository.watchers));
        holder.textStars.setText(
                context.getResources().getString(R.string.text_stars, holder.repository.stars));
        holder.textForks.setText(
                context.getString(R.string.text_forks, holder.repository.forks));

        holder.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.repository);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class RepositoryViewHolder extends RecyclerView.ViewHolder {
        public Repository repository;

        @BindView(R.id.text_repo_title)
        TextView textRepoTitle;
        @BindView(R.id.text_repo_description)
        TextView textRepoDescription;
        @BindView(R.id.text_watchers)
        TextView textWatchers;
        @BindView(R.id.text_stars)
        TextView textStars;
        @BindView(R.id.text_forks)
        TextView textForks;
        @BindView(R.id.layout_content)
        LinearLayout layoutContent;
        @BindView(R.id.card_view)
        CardView cardView;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Repository repository);
    }
}
