package com.rad21.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rad21.githubuser.database.Favorite
import com.rad21.githubuser.repository.FavoriteRepository

final class FavoriteViewModel (application: Application): ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorited(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()

}