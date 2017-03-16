package com.example.archimvp.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.example.archimvp.R;
import com.example.archimvp.model.Repository;
import com.example.archimvp.model.User;
import com.example.archimvp.utils.StringUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by LeoPoldCrossing on 2017/3/17.
 */

public class RepositoryViewModel extends BaseObservable {
    private Repository mRepository;
    private Context mContext;
    private User user;

    public RepositoryViewModel(Context context, Repository repository) {
        mRepository = repository;
        mContext = context;
    }

    public String getDescription() {
        return mRepository.description;
    }

    public String getHomepage() {
        return mRepository.homepage;
    }

    public String getLanguage() {
        return mContext.getString(R.string.text_language, mRepository.language);
    }

    public boolean hasLanguage(){
        return mRepository.hasLanguage();
    }

    public Boolean isFork() {
        return mRepository.isFork();
    }

    public String getOwnerAvatarUrl() {
        return mRepository.owner.avatarUrl;
    }

    @Bindable
    public String getUserName() {
        if (user == null) {
            return null;
        }
        return user.name;
    }

    @Bindable
    public String getUserEmail() {
        if (user == null) {
            return null;
        }
        return user.email;
    }

    @Bindable
    public String getUserLocation() {
        if (user == null) {
            return null;
        }
        return user.location;
    }

    @Bindable
    public boolean isShowUser(){
        if (user == null) {
            return false;
        }
        return !StringUtil.isEmpty(user.name) || !StringUtil.isEmpty(user.email) || !StringUtil.isEmpty(user.location);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view , String imageUrl){
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    public void setUser(User user) {
        this.user = user;
        notifyPropertyChanged(BR.userName);
        notifyPropertyChanged(BR.userEmail);
        notifyPropertyChanged(BR.userLocation);
        notifyPropertyChanged(BR.showUser);
    }

}
