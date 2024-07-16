package com.odisby.goldentomatoes.data.data.repositories

import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import kotlinx.coroutines.flow.Flow

interface SavedRepository {
    suspend fun getSavedMovies(): Flow<List<MovieGlobal>>

    suspend fun addSavedMovie(movie: MovieGlobal)

    suspend fun removeSavedMovie(movieId: Long)

    suspend fun getMoviesById(movieId: Long): Flow<MovieGlobal?>
}
