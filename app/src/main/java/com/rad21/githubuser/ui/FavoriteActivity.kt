package com.rad21.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rad21.githubuser.R
import com.rad21.githubuser.adapter.FavoriteAdapter
import com.rad21.githubuser.databinding.ActivityFavoriteBinding
import com.rad21.githubuser.factory.FavoriteViewModelFactory
import com.rad21.githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _favoriteActivityBinding: ActivityFavoriteBinding? = null
    private val binding get() = _favoriteActivityBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _favoriteActivityBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val favoriteViewMode = obtainViewModel(this@FavoriteActivity)
        favoriteViewMode.getAllFavorited().observe(this) {favoriteList ->
            adapter.setListFavorited(favoriteList)
        }

        adapter = FavoriteAdapter()
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = adapter


        binding?.topAppBar?.setOnClickListener {
            onBackPressed()
        }

        binding?.topAppBar?.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.settings -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteActivityBinding = null
    }

}