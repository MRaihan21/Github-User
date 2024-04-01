package com.rad21.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rad21.githubuser.data.response.UserFollowingResponseItem
import com.rad21.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    private val _followingList = MutableLiveData<List<UserFollowingResponseItem>>()
    val followingList : LiveData<List<UserFollowingResponseItem>> = _followingList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowingListViewModel"
    }

    fun listFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<UserFollowingResponseItem>> {

            override fun onResponse(
                call: Call<List<UserFollowingResponseItem>>,
                response: Response<List<UserFollowingResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followingList.value = response.body()
                } else {
                    Log.e(TAG, "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

}