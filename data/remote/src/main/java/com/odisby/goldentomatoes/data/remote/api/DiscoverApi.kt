package com.odisby.goldentomatoes.data.remote.api

import com.odisby.goldentomatoes.data.data.model.DiscoverMoviesRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getDiscoverMovies(): Response<DiscoverMoviesRemote>

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getRandomMovie(@Query("page") randomPage: Int): Response<DiscoverMoviesRemote>
}
