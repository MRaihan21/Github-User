package com.rad21.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rad21.githubuser.adapter.SectionsPagerAdapter
import com.rad21.githubuser.data.response.DetailUserResponse
import com.rad21.githubuser.databinding.ActivityDetailBinding

class DetailUserActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailBinding

    companion object {

        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val githubUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)

        val githubUsername = intent.getStringExtra(EXTRA_USER)
        githubUserViewModel.fetcUserDetail(githubUsername.toString())

        val bundle = Bundle()
        bundle.putString(EXTRA_USER, githubUsername)

        githubUserViewModel.userDetail.observe(this){ user ->
            displayUserDetail(user)
        }
        githubUserViewModel.isLoading.observe(this){
            showProgressBar(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager2)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {
                tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun displayUserDetail(user: DetailUserResponse) {
        binding.tvNameDetail.text = user.name
        binding.tvUsernameDetail.text = user.login
        binding.tvFollowing.text = "${user.following} Following"
        binding.tvFollowers.text = "${user.followers} Followers"
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.ivDetail)
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}