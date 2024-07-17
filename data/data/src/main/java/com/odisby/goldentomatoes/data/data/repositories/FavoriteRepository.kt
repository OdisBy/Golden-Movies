package com.odisby.goldentomatoes.data.data.repositories

import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun getFavoriteMovies(): Flow<List<MovieGlobal>>

    suspend fun addFavoriteMovie(movie: MovieGlobal)

    suspend fun removeFavoriteMovie(movieId: Long)

    suspend fun getMoviesById(movieId: Long): Flow<MovieGlobal?>

    suspend fun setScheduledStatus(movieId: Long, newState: Boolean)
}
