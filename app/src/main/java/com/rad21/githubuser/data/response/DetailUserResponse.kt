package com.rad21.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("bio")
	val bio: Any,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String,
)
