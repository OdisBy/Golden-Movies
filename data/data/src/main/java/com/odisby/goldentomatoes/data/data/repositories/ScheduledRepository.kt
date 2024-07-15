package com.odisby.goldentomatoes.data.data.repositories

import com.odisby.goldentomatoes.data.data.model.MovieGlobal

interface ScheduledRepository {
    suspend fun getScheduledMovies(): List<MovieGlobal>

    suspend fun addScheduledMovie(movie: MovieGlobal)

    suspend fun removeScheduledMovie(movieId: Long)

    suspend fun getMoviesById(movieId: Long): MovieGlobal?
}
