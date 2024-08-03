package com.aetherinsight.goldentomatoes.data.remote.api

import com.aetherinsight.goldentomatoes.data.data.model.MovieRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApi {

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Long): Response<MovieRemote>
}
