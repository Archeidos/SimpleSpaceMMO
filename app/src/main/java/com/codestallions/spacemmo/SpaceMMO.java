package com.codestallions.spacemmo;

import android.app.Application;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SpaceMMO extends Application {

    private static FirebaseAuth authentication;

    //Add Dagger here

    @Override
    public void onCreate() {
        super.onCreate();
        authentication = FirebaseAuth.getInstance();
    }

    public static FirebaseAuth getAuth() {
        return authentication;
    }
}
