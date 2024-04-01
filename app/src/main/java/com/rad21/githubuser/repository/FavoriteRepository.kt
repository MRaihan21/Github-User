package com.rad21.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rad21.githubuser.database.Favorite
import com.rad21.githubuser.database.FavoriteDao
import com.rad21.githubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {

    private val userFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        userFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = userFavoriteDao.getAllFavorite()

    fun getAllFavoriteByName(username: String): LiveData<Favorite> = userFavoriteDao.getAllFavoriteByName(username)

    fun insert(favorite: Favorite) {
        executorService.execute { userFavoriteDao.insert(favorite) }
    }
    fun delete(favorite: Favorite) {
        executorService.execute { userFavoriteDao.delete(favorite) }
    }
    fun update(favorite: Favorite) {
        executorService.execute { userFavoriteDao.update(favorite) }
    }

}