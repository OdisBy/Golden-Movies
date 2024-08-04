package com.aetherinsight.goldentomatoes.data.data.repositories

import com.aetherinsight.goldentomatoes.data.data.model.SearchMovieRemote
import kotlinx.coroutines.flow.Flow

interface SearchMoviesRepository {
    suspend fun searchMovies(query: String): Flow<List<SearchMovieRemote>>
}