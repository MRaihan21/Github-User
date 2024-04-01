package com.rad21.githubuser.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rad21.githubuser.R
import com.rad21.githubuser.adapter.UserAdapter
import com.rad21.githubuser.data.response.ItemsItem
import com.rad21.githubuser.databinding.ActivityMainBinding
import com.rad21.githubuser.factory.SettingViewModelFactory
import com.rad21.githubuser.setting.SettingPreferences
import com.rad21.githubuser.setting.datastore
import com.rad21.githubuser.viewmodel.MainViewModel
import com.rad21.githubuser.viewmodel.SettingViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )

        val layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvMain.addItemDecoration(itemDecoration)

        userAdapter = UserAdapter()
        binding.rvMain.adapter = userAdapter

        mainViewModel.users.observe(this) { listuser ->
            setUsersData(listuser)
        }

        mainViewModel.isLoadingIndicator.observe(this) {
            showLoadingIndicator(it)
        }

        with(binding) {
            val menu = binding.topAppBar.menu
            val setting = menu.findItem(R.id.settings)
            val favorite = menu.findItem(R.id.favorites)
            val darkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            if (darkMode == Configuration.UI_MODE_NIGHT_YES){
                setting.setIcon(R.drawable.baseline_settings_24_white)
                favorite.setIcon(R.drawable.baseline_favorite_24_white)
            } else {
                setting.setIcon(R.drawable.baseline_settings_24)
                favorite.setIcon(R.drawable.baseline_favorite_24)
            }

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    val userName = searchView.text.toString()
                    mainViewModel.findUsers(userName)
                    searchView.hide()
                    searchView.hide()
                    false
                }

        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.settings -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    true
                }
                R.id.favorites -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }

    private fun setUsersData(user: List<ItemsItem>) {
        val usersAdapter = UserAdapter()
        usersAdapter.submitList(user)
        binding.rvMain.adapter = usersAdapter
    }

    private fun showLoadingIndicator(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}