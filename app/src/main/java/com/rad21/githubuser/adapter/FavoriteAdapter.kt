package com.rad21.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rad21.githubuser.database.Favorite
import com.rad21.githubuser.databinding.ItemUserBinding
import com.rad21.githubuser.helper.FavoriteDiffCallback
import com.rad21.githubuser.ui.DetailUserActivity
import com.squareup.picasso.Picasso

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    private val listFavorite = ArrayList<Favorite>()

    fun setListFavorited(listFavorites: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite){
            val ff = binding.root.context
            with(binding) {
                tvUsers.text = favorite.username
                Picasso.get().load(favorite.avatar).into(binding.ivUsers)
                itemCard.setOnClickListener {
                    val intent = Intent(ff, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USER, favorite.username)
                    intent.putExtra(DetailUserActivity.EXTRA_IMG, favorite.avatar)
                    intent.putExtra(DetailUserActivity.EXTRA_FAVORITE, favorite)
                    ff.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }
}