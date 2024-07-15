package com.odisby.goldentomatoes.data.data.repositories

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.MovieGlobal

interface DiscoverRepository {
    suspend fun getDiscoverMovies(): Resource<List<MovieGlobal>>

    suspend fun randomMovieId(): Long?
}