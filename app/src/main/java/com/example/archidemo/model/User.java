package com.example.archidemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */
public class User implements Parcelable {
    public long id;
    public String name;
    public String url;
    public String email;
    public String login;
    public String location;
    @SerializedName("avatar_url")
    public String avatarUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean hasEmail() {
        return email != null && !email.isEmpty();
    }

    public boolean hasLocation() {
        return location != null && !location.isEmpty();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.login);
        dest.writeString(this.location);
        dest.writeString(this.avatarUrl);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.login = in.readString();
        this.location = in.readString();
        this.avatarUrl = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", location='" + location + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
