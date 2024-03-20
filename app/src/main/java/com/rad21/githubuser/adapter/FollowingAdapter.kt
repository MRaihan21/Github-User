package com.rad21.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rad21.githubuser.data.response.UserFollowingResponseItem
import com.rad21.githubuser.databinding.ItemUserBinding

class FollowingAdapter: ListAdapter<UserFollowingResponseItem, FollowingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowingResponseItem>(){
            override fun areItemsTheSame(
                oldItem: UserFollowingResponseItem,
                newItem: UserFollowingResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserFollowingResponseItem,
                newItem: UserFollowingResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class MyViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(following: UserFollowingResponseItem) {
            binding.tvUsers.text = following.login
            Glide.with(itemView)
                .load(following.avatarUrl)
                .into(binding.ivUsers)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val following = getItem(position)
        holder.bind(following)
    }
}