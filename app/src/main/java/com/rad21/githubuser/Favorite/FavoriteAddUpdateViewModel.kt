package com.rad21.githubuser.Favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rad21.githubuser.database.Favorite
import com.rad21.githubuser.repository.FavoriteRepository

class FavoriteAddUpdateViewModel(application: Application): ViewModel() {

    private val userFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavoriteByName(username: String) : LiveData<Favorite> = userFavoriteRepository.getAllFavoriteByName(username)

    fun insert(favorite: Favorite) {
        userFavoriteRepository.insert(favorite)
    }
    fun update(favorite: Favorite) {
        userFavoriteRepository.update(favorite)
    }
    fun delete(favorite: Favorite) {
        userFavoriteRepository.delete(favorite)
    }

}