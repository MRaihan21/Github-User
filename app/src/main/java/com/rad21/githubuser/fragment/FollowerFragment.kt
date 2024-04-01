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
import com.rad21.githubuser.adapter.FollowerAdapter
import com.rad21.githubuser.data.response.UserFollowersResponseItem
import com.rad21.githubuser.databinding.FragmentFollowerBinding
import com.rad21.githubuser.viewmodel.FollowerViewModel

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var followerAdapter: FollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argumentBundle = arguments
        val userName = argumentBundle?.getString(DetailUserActivity.EXTRA_USER).toString()

        val followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowerViewModel::class.java)

        binding = FragmentFollowerBinding.bind(view)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowers.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
        binding.rvFollowers.addItemDecoration(dividerItemDecoration)

        followerAdapter = FollowerAdapter()
        binding.rvFollowers.adapter = followerAdapter

        followersViewModel.listFollower(userName)

        followersViewModel.follower.observe(viewLifecycleOwner) {
            followerList -> setFollower(followerList)
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollower(follower: List<UserFollowersResponseItem>) {
        val adapter = FollowerAdapter()
        adapter.submitList(follower)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollowers.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}