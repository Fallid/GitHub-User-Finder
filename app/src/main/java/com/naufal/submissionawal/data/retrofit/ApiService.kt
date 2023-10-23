package com.naufal.submissionawal.data.retrofit

import com.naufal.submissionawal.data.response.FollowersResponse
import com.naufal.submissionawal.data.response.FollowingResponse
import com.naufal.submissionawal.data.response.SearchResponse
import com.naufal.submissionawal.data.response.UserContent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?q")
    @Headers("Authorization: token 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")
    fun getUsers(@Query("q") query: String ): Call<ListDataUser>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(@Path("username") username: String) :Call<UserContent>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String) : Call<List<FollowersResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String) : Call<List<FollowingResponse>>
}

data class ListDataUser(
    val indexCount: Int = 0,
    val items: List<SearchResponse>
)
