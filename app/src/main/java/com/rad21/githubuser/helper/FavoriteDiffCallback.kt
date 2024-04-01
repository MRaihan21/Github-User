package com.rad21.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.rad21.githubuser.database.Favorite

class FavoriteDiffCallback(private val oldList: List<Favorite>, private val newList: List<Favorite>):DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldList[oldItemPosition]
        val newFavorite = newList[newItemPosition]
        return oldFavorite.username == newFavorite.username && oldFavorite.avatar == newFavorite.avatar
    }


}