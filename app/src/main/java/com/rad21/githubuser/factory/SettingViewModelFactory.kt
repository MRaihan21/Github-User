package com.rad21.githubuser.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rad21.githubuser.setting.SettingPreferences
import com.rad21.githubuser.viewmodel.SettingViewModel
import java.lang.IllegalArgumentException

class SettingViewModelFactory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }


}