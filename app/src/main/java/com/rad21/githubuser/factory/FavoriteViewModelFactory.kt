package com.rad21.githubuser.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rad21.githubuser.Favorite.FavoriteAddUpdateViewModel
import com.rad21.githubuser.viewmodel.FavoriteViewModel

class FavoriteViewModelFactory private constructor(private val application: Application): ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var INSTANCE: FavoriteViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): FavoriteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteViewModelFactory::class.java) {
                    INSTANCE = FavoriteViewModelFactory(application)
                }
            }
            return INSTANCE as FavoriteViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application) as T
        } else if (modelClass.isAssignableFrom(FavoriteAddUpdateViewModel::class.java)) {
            return FavoriteAddUpdateViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}