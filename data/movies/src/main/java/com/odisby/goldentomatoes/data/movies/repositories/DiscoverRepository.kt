package com.odisby.goldentomatoes.data.movies.repositories

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.movies.remote.model.MovieRemote


interface DiscoverRepository {
    suspend fun getDiscoverMovies(): Resource<List<MovieRemote>>

    suspend fun randomMovieId(): Long?
}