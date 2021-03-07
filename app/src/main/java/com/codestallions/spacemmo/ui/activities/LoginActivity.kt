package com.codestallions.spacemmo.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codestallions.spacemmo.R
import com.codestallions.spacemmo.ui.fragments.CreateAccountFragment
import com.codestallions.spacemmo.ui.fragments.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val newFragment: Fragment = LoginFragment()
        supportFragmentManager.beginTransaction().add(R.id.login_root_container, newFragment).commit()
    }

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments
        for (fragment in fragmentList) {
            if (fragment is CreateAccountFragment) {
                super.onBackPressed()
            }
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}