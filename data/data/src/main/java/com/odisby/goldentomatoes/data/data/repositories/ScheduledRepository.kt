package com.odisby.goldentomatoes.data.data.repositories

import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import kotlinx.coroutines.flow.Flow

interface ScheduledRepository {
    suspend fun getScheduledMovies(): Flow<List<MovieGlobal>>

    suspend fun addScheduledMovie(movie: MovieGlobal)

    suspend fun removeScheduledMovie(movieId: Long)

    suspend fun getMoviesById(movieId: Long): Flow<MovieGlobal?>
}
