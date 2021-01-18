package com.codestallions.spacemmo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AuthUserModel implements Parcelable {

    public static final Creator<AuthUserModel> CREATOR = new Creator<AuthUserModel>() {
        @Override
        public AuthUserModel createFromParcel(Parcel in) {
            return new AuthUserModel(in);
        }

        @Override
        public AuthUserModel[] newArray(int size) {
            return new AuthUserModel[size];
        }
    };

    private String uid;
    private String name;
    private String email;

    public AuthUserModel(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    protected AuthUserModel(Parcel in) {
        uid = in.readString();
        name = in.readString();
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(email);
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
