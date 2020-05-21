package com.codestallions.spacemmo.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.ui.fragments.CreateAccountFragment;
import com.codestallions.spacemmo.ui.fragments.LoginFragment;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Fragment newFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.login_root_container, newFragment).commit();
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof CreateAccountFragment) {
                super.onBackPressed();
            }
        }
    }

}
