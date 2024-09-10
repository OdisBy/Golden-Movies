package com.aetherinsight.goldentomatoes.data.data.source

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import kotlinx.coroutines.flow.Flow

interface FavoriteMoviesSource {
    interface Local : FavoriteMoviesSource {
        fun getFavoriteMovies(): Flow<List<MovieGlobal>>
        suspend fun addFavoriteMovie(movie: MovieGlobal)
        suspend fun removeFavoriteMovie(movieId: Long)
        suspend fun getMoviesById(movieId: Long): MovieGlobal?
        suspend fun setScheduledStatus(movieId: Long, newState: Boolean)
    }
}
