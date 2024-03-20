package com.rad21.githubuser.data.retrofit

import com.rad21.githubuser.data.response.DetailUserResponse
import com.rad21.githubuser.data.response.UserFollowersResponseItem
import com.rad21.githubuser.data.response.UserFollowingResponseItem
import com.rad21.githubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getListUser(@Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollower(@Path("username") username: String
    ): Call<List<UserFollowersResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String
    ): Call<List<UserFollowingResponseItem>>

}