package com.odisby.goldentomatoes.data.data.repositories

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getDiscoverMovies(): Flow<Resource<List<MovieGlobal>>>

    suspend fun randomMovieId(): Long?
}