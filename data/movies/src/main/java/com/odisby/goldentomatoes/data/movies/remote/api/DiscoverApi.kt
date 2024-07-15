package com.odisby.goldentomatoes.data.movies.remote.api

import com.odisby.goldentomatoes.data.movies.remote.model.DiscoverMoviesRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface DiscoverApi {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getDiscoverMovies(): Response<DiscoverMoviesRemote>

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getRandomMovie(@Query("page") randomPage: Int): Response<DiscoverMoviesRemote>
}
