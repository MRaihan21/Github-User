package com.rad21.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.rad21.githubuser.R
import com.rad21.githubuser.factory.SettingViewModelFactory
import com.rad21.githubuser.setting.SettingPreferences
import com.rad21.githubuser.setting.datastore
import com.rad21.githubuser.viewmodel.SettingViewModel

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val pref = SettingPreferences.getInstance(application.datastore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[
            SettingViewModel::class.java
        ]
        settingViewModel.getThemeSettings().observe(this) {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)

    }
}