package com.odisby.goldentomatoes.data.movies.repositories

import com.odisby.goldentomatoes.data.movies.both.model.SearchMovieRemote

interface SearchMoviesRepository {
    /**
     * Search discover movies on remote and on local database
     * @return SearchMoviesRemote with scheduled and usual movies parameters
     */
    suspend fun searchMovies(query: String): List<SearchMovieRemote>
}