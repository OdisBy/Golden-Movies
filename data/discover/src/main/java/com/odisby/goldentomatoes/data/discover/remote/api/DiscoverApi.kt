package com.odisby.goldentomatoes.data.discover.remote.api

import com.odisby.goldentomatoes.data.discover.remote.model.DiscoverMoviesRemote
import retrofit2.Response
import retrofit2.http.GET

internal interface DiscoverApi {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getDiscoverMovies(): Response<DiscoverMoviesRemote>
}