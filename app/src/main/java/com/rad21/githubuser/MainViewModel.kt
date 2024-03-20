package com.rad21.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rad21.githubuser.data.response.ItemsItem
import com.rad21.githubuser.data.response.UserResponse
import com.rad21.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _users = MutableLiveData<List<ItemsItem>>()
    val users: LiveData<List<ItemsItem>> = _users

    private val _isLoadingIndicator = MutableLiveData<Boolean>()
    val isLoadingIndicator: LiveData<Boolean> = _isLoadingIndicator

    companion object {
        private const val TAG = "MainViewModel"
        private const val USERNAME = "RAIHAN"
    }

    init {
        findUsers(USERNAME)
    }

    fun findUsers(username: String) {

        _isLoadingIndicator.value = true
        val client = ApiConfig.getApiService().getListUser(username)
        client.enqueue(object : retrofit2.Callback<UserResponse> {

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoadingIndicator.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoadingIndicator.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}