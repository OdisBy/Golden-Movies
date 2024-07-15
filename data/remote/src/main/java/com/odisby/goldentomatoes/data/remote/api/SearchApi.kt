package com.odisby.goldentomatoes.data.remote.api

import com.odisby.goldentomatoes.data.data.model.SearchRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String
    ): Response<SearchRemote>
}
