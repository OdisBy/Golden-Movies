package com.odisby.goldentomatoes.data.movies.remote.api

import com.odisby.goldentomatoes.data.movies.remote.model.MovieRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface DetailsApi {

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Long): Response<MovieRemote>
}
