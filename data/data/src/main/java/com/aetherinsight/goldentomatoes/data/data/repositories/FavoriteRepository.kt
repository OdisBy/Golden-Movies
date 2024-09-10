package com.aetherinsight.goldentomatoes.data.data.repositories

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteMovies(): Flow<Resource<List<MovieGlobal>>>

    suspend fun addFavoriteMovie(movie: MovieGlobal)

    suspend fun removeFavoriteMovie(movieId: Long)

    suspend fun getMoviesById(movieId: Long): Flow<MovieGlobal?>

    suspend fun setScheduledStatus(movieId: Long, newState: Boolean)
}
