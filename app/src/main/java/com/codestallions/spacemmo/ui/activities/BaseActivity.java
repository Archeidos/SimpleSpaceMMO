package com.codestallions.spacemmo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codestallions.spacemmo.SessionManager;
import com.codestallions.spacemmo.SpaceMMO;

public abstract class BaseActivity extends AppCompatActivity {

    private static final long SESSION_CHECK_INTERVAL = 30000;
    private Handler sessionCheckHandler;

    private Runnable sessionCheckRunnable = new Runnable()
    {
        public void run()
        {
            if (SpaceMMO.getSession().isLoginExpired(getApplicationContext()) &&
                    SpaceMMO.getAuth().getCurrentUser() != null) {
                SpaceMMO.getAuth().signOut();
                navigateToLogin();
            }
            sessionCheckHandler.postDelayed(this, SESSION_CHECK_INTERVAL);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionCheckHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSessionWatcher();
    }

    private void startSessionWatcher() {
        sessionCheckHandler.postDelayed(sessionCheckRunnable, SESSION_CHECK_INTERVAL);
    }

    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
