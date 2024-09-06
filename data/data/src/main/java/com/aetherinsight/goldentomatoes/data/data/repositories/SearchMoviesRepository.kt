package com.aetherinsight.goldentomatoes.data.data.repositories

import com.aetherinsight.goldentomatoes.core.data.model.SearchMovie
import kotlinx.coroutines.flow.Flow

interface SearchMoviesRepository {
    suspend fun searchMovies(query: String): Flow<List<SearchMovie>>
}
