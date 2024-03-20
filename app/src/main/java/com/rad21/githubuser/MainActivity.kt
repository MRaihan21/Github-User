package com.rad21.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rad21.githubuser.adapter.UserAdapter
import com.rad21.githubuser.data.response.ItemsItem
import com.rad21.githubuser.databinding.ActivityMainBinding

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