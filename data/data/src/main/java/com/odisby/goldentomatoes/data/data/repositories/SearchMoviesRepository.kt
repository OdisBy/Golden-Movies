package com.odisby.goldentomatoes.data.data.repositories

import com.odisby.goldentomatoes.data.data.model.SearchMovieRemote

interface SearchMoviesRepository {
    suspend fun searchMovies(query: String): List<SearchMovieRemote>
}
