package com.rad21.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rad21.githubuser.data.response.UserFollowersResponseItem
import com.rad21.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {

    private val _followerList = MutableLiveData<List<UserFollowersResponseItem>>()
    val follower: LiveData<List<UserFollowersResponseItem>> = _followerList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun listFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object : Callback<List<UserFollowersResponseItem>> {

            override fun onResponse(
                call: Call<List<UserFollowersResponseItem>>,
                response: Response<List<UserFollowersResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followerList.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        const val TAG = "FollowersViewModel"
    }

}