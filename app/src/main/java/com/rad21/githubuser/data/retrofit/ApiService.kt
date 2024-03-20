package com.rad21.githubuser.data.retrofit

import com.rad21.githubuser.data.response.DetailUserResponse
import com.rad21.githubuser.data.response.UserFollowersResponseItem
import com.rad21.githubuser.data.response.UserFollowingResponseItem
import com.rad21.githubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getListUser(@Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollower(@Path("username") username: String
    ): Call<List<UserFollowersResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String
    ): Call<List<UserFollowingResponseItem>>

}