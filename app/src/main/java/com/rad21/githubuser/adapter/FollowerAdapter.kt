package com.rad21.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rad21.githubuser.data.response.UserFollowersResponseItem
import com.rad21.githubuser.databinding.ItemUserBinding

class FollowerAdapter : ListAdapter<UserFollowersResponseItem, FollowerAdapter.FollowerViewHolder>(DIFF_CALLBACK){

    class FollowerViewHolder(val viewBinding: ItemUserBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(follower: UserFollowersResponseItem) {
            viewBinding.tvUsers.text = follower.login
            Glide.with(itemView)
                .load(follower.avatarUrl)
                .into(viewBinding.ivUsers)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val viewBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val follower = getItem(position)
        holder.bind(follower)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowersResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserFollowersResponseItem,
                newItem: UserFollowersResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserFollowersResponseItem,
                newItem: UserFollowersResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}