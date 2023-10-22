package com.naufal.submissionawal.data.retrofit

import com.naufal.submissionawal.data.response.FollowersResponse
import com.naufal.submissionawal.data.response.FollowingResponse
import com.naufal.submissionawal.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?q")
    @Headers("Authorization: token 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")
    fun getUsers(@Query("q") query: String ): Call<ListDataUser>


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
