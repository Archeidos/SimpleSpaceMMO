package com.codestallions.spacemmo.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.codestallions.spacemmo.SpaceMMO
import com.codestallions.spacemmo.ui.activities.LoginActivity

abstract class BaseActivity : AppCompatActivity() {
    private var sessionCheckHandler: Handler? = null
    private val sessionCheckRunnable: Runnable = object : Runnable {
        override fun run() {
            if (SpaceMMO.getSession().isLoginExpired(applicationContext) &&
                    SpaceMMO.getAuth().currentUser != null) {
                SpaceMMO.getAuth().signOut()
                navigateToLogin()
            }
            sessionCheckHandler!!.postDelayed(this, SESSION_CHECK_INTERVAL)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionCheckHandler = Handler(Looper.getMainLooper())
    }

    override fun onResume() {
        super.onResume()
        startSessionWatcher()
    }

    private fun startSessionWatcher() {
        sessionCheckHandler!!.postDelayed(sessionCheckRunnable, SESSION_CHECK_INTERVAL)
    }

    fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    companion object {
        private const val SESSION_CHECK_INTERVAL: Long = 30000
    }
}