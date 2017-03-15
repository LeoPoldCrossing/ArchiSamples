package com.example.archidemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

public class Repository implements Parcelable {
    public long id;
    public String name;
    public String description;
    public int forks;
    public int watchers;
    @SerializedName("stargazers_count")
    public int stars;
    public String language;
    public String homepage;
    public User owner;
    public boolean fork;


    @Override
    public int describeContents() {
        return 0;
    }

    public boolean hasHomepage() {
        return homepage != null && !homepage.isEmpty();
    }

    public boolean hasLanguage() {
        return language != null && !language.isEmpty();
    }

    public boolean isFork() {
        return fork;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.forks);
        dest.writeInt(this.watchers);
        dest.writeInt(this.stars);
        dest.writeString(this.language);
        dest.writeString(this.homepage);
        dest.writeParcelable(this.owner, flags);
        dest.writeByte(this.fork ? (byte) 1 : (byte) 0);
    }

    public Repository() {
    }

    protected Repository(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.description = in.readString();
        this.forks = in.readInt();
        this.watchers = in.readInt();
        this.stars = in.readInt();
        this.language = in.readString();
        this.homepage = in.readString();
        this.owner = in.readParcelable(User.class.getClassLoader());
        this.fork = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Repository> CREATOR = new Parcelable.Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel source) {
            return new Repository(source);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", descrption='" + description + '\'' +
                ", forks=" + forks +
                ", watchers=" + watchers +
                ", stars=" + stars +
                ", language='" + language + '\'' +
                ", homepage='" + homepage + '\'' +
                ", owner=" + owner +
                ", fork=" + fork +
                '}';
    }
}
