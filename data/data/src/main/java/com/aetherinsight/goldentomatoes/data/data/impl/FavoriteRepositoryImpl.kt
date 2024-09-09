package com.aetherinsight.goldentomatoes.data.data.impl

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.data.data.source.FavoriteMoviesSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteMoviesSource.Local
) : FavoriteRepository {

    private val cache = mutableListOf<MovieGlobal>()

    override fun getFavoriteMovies(): Flow<List<MovieGlobal>> = flow {
        if (cache.isEmpty()) {
            val movies = localDataSource.getFavoriteMovies().firstOrNull() ?: emptyList()
            cache.addAll(movies)
            emit(cache)
        } else {
            emit(cache)
        }

        localDataSource.getFavoriteMovies()
            .distinctUntilChanged()
            .collect { movies ->
                cache.clear()
                cache.addAll(movies)
                emit(movies)
            }
    }

    override suspend fun addFavoriteMovie(movie: MovieGlobal) {
        localDataSource.addFavoriteMovie(movie)
    }

    override suspend fun removeFavoriteMovie(movieId: Long) {
        localDataSource.removeFavoriteMovie(movieId)
    }

    override suspend fun getMoviesById(movieId: Long): Flow<MovieGlobal?> = flow {
        emit(localDataSource.getMoviesById(movieId))
    }

    override suspend fun setScheduledStatus(movieId: Long, newState: Boolean) {
        localDataSource.setScheduledStatus(movieId, newState)
    }
}
