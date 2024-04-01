package com.rad21.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.rad21.githubuser.R
import com.rad21.githubuser.databinding.ActivitySettingBinding
import com.rad21.githubuser.viewmodel.SettingViewModel
import com.rad21.githubuser.setting.SettingPreferences
import com.rad21.githubuser.setting.datastore
import com.rad21.githubuser.factory.SettingViewModelFactory

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnClickListener {
            onBackPressed()
        }

        val switchTheme = binding.switchTheme

        val pref = SettingPreferences.getInstance(application.datastore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActivity: Boolean ->
            if (isDarkModeActivity){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.text = "Dark Mode"
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.text = "Ligth Mode"
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

    }

}