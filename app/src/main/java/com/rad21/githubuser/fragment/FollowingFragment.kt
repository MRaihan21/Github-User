package com.rad21.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rad21.githubuser.ui.DetailUserActivity
import com.rad21.githubuser.adapter.FollowingAdapter
import com.rad21.githubuser.data.response.UserFollowingResponseItem
import com.rad21.githubuser.databinding.FragmentFollowingBinding
import com.rad21.githubuser.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments
        val userName = data?.getString(DetailUserActivity.EXTRA_USER).toString()

        val followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java
        )

        binding = FragmentFollowingBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        adapter = FollowingAdapter()
        binding.rvFollowing.adapter = adapter

        followingViewModel.listFollowing(userName)

        followingViewModel.followingList.observe(viewLifecycleOwner) {
            listFollower -> setFollowing(listFollower)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setFollowing(following: List<UserFollowingResponseItem>){
        val adapter = FollowingAdapter()
        adapter.submitList(following)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollowing.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}