package com.aetherinsight.goldentomatoes.data.data.repositories

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.model.MovieGlobal
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getDiscoverMovies(): Flow<Resource<List<MovieGlobal>>>

    suspend fun randomMovieId(): Long?
}