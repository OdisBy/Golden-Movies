package com.odisby.goldentomatoes.data.discover.remote.api

import com.odisby.goldentomatoes.data.discover.remote.model.SearchRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchApi {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String
    ): Response<SearchRemote>
}
