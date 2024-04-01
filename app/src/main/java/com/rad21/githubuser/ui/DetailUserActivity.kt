package com.rad21.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rad21.githubuser.Favorite.FavoriteAddUpdateViewModel
import com.rad21.githubuser.R
import com.rad21.githubuser.adapter.SectionsPagerAdapter
import com.rad21.githubuser.data.response.DetailUserResponse
import com.rad21.githubuser.database.Favorite
import com.rad21.githubuser.databinding.ActivityDetailBinding
import com.rad21.githubuser.factory.FavoriteViewModelFactory
import com.rad21.githubuser.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {

    private var isEdit = false
    private var favorited: Favorite? = null

    private lateinit var binding: ActivityDetailBinding
    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getName = intent.getStringExtra(EXTRA_USER)
        val getImg = intent.getStringExtra(EXTRA_IMG)

        binding.topAppBarDetail.setOnClickListener {
            onBackPressed()
        }

        binding.topAppBarDetail.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.settings -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                    true
                }
                R.id.favorites -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    true
                }
                else -> false
            }
        }

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

        favoriteAddUpdateViewModel = obtainViewModel(this@DetailUserActivity)

        favorited = intent.getParcelableExtra(EXTRA_FAVORITE)

        if (favorited != null) {
            isEdit = true
        } else {
            favorited = Favorite()
        }

        favoriteAddUpdateViewModel.getAllFavoriteByName(getName.toString()).observe(this, Observer { favoriteData ->
            if (favoriteData != null) {
                isEdit = true
                favorited = Favorite(favoriteData.id, favoriteData.username, favoriteData.avatar)
                Log.d("test flag 1","Loged data != null cuy")
                binding.floatingLove.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                favorited = Favorite()
                Log.d("test flag 2","Loged data null cuy")
                binding.floatingLove.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        })

        binding.floatingLove.setOnClickListener {
            favorited?.let { favorit ->
                favorit.username = getName.toString()
                favorit.avatar = getImg
            }
            if (isEdit) {
                favoriteAddUpdateViewModel.delete(favorited as Favorite)
                showToast("Deleted loved account!")
                Log.d("test flag 1","mencoba hapus data")
                binding.floatingLove.setImageResource(R.drawable.baseline_favorite_border_24)
                isEdit = false
            } else {
                favoriteAddUpdateViewModel.insert(favorited as Favorite)
                showToast("New Loved Account")
                binding.floatingLove.setImageResource(R.drawable.baseline_favorite_24)
                isEdit = true
            }
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddUpdateViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddUpdateViewModel::class.java)
    }

    companion object {

        const val EXTRA_USER = "extra_user"
        const val EXTRA_IMG = "extra_img"
        const val EXTRA_FAVORITE = "extra_favorite"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}