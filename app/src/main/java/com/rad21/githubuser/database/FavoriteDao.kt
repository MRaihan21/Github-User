package com.rad21.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)
    @Update
    fun update(favorite: Favorite)
    @Delete
    fun delete(favorite: Favorite)
    @Query("SELECT * from favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE username = :username ")
    fun getAllFavoriteByName(username: String): LiveData<Favorite>

}