package com.naufal.submissionawal.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class SearchResponse(
    @field:SerializedName("following_url")
    val followingUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("followers_url")
    val followersUrl: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    ) : Parcelable

@Parcelize
data class UserContent(
    val name: String,
    val login: String,
    val avatar_url: String,
    val followers: Int,
    val following: Int,
) : Parcelable

@Parcelize
data class FollowersResponse(
    @field:SerializedName("avatar_url")
    val followersUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,
) :Parcelable

@Parcelize
data class FollowingResponse(
    @field:SerializedName("avatar_url")
    val followersUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,
) :Parcelable
