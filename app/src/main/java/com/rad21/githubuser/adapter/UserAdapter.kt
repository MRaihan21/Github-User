package com.rad21.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rad21.githubuser.DetailUserActivity
import com.rad21.githubuser.data.response.ItemsItem
import com.rad21.githubuser.databinding.ItemUserBinding

class UserAdapter: ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem){
            binding.tvUsers.text = user.login
            Glide.with(itemView)
                .load(user.avatarUrl)
                .into(binding.ivUsers)

            val clickUser = binding.root.context
            itemView.setOnClickListener {
                val intent = Intent(clickUser, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, user.login)
                clickUser.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
