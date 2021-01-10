package com.codestallions.spacemmo;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

public class SpaceMMO extends Application {

    private static FirebaseAuth auth;

    //Add Dagger here

    @Override
    public void onCreate() {
        super.onCreate();
        auth = FirebaseAuth.getInstance();
    }

    public static FirebaseAuth getAuth() {
        return auth;
    }
}
