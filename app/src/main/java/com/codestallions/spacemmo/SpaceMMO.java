package com.codestallions.spacemmo;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

public class SpaceMMO extends Application {

    private static FirebaseAuth auth;
    private static SessionManager sessionManager;

    //Add Dagger here

    @Override
    public void onCreate() {
        super.onCreate();
        auth = FirebaseAuth.getInstance();
        sessionManager = SessionManager.getInstance();
    }

    public static FirebaseAuth getAuth() {
        return auth;
    }

    public static SessionManager getSession() {
        return sessionManager;
    }
}
