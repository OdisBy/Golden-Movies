package com.aetherinsight.goldentomatoes.data.data.impl

import com.aetherinsight.goldentomatoes.data.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.data.data.source.FavoriteMoviesSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteMoviesSource.Local
) : FavoriteRepository {

    private val favoriteMovies = mutableListOf<MovieGlobal>()

    override suspend fun getFavoriteMovies(): Flow<List<MovieGlobal>> = flow {
        if (favoriteMovies.isEmpty()) {
            val movies = localDataSource.getFavoriteMovies()
            favoriteMovies.addAll(movies)
        }

        emit(favoriteMovies)
    }

    override suspend fun addFavoriteMovie(movie: MovieGlobal) {
        localDataSource.addFavoriteMovie(movie)
        favoriteMovies.add(movie)
    }

    override suspend fun removeFavoriteMovie(movieId: Long) {
        localDataSource.removeFavoriteMovie(movieId)
        favoriteMovies.removeIf { it.id == movieId }
    }

    override suspend fun getMoviesById(movieId: Long): Flow<MovieGlobal?> = flow {
        val cacheMovie = favoriteMovies.find { it.id == movieId }
        if (cacheMovie != null) emit(cacheMovie)

        emit(localDataSource.getMoviesById(movieId))
    }

    override suspend fun setScheduledStatus(movieId: Long, newState: Boolean) {
        localDataSource.setScheduledStatus(movieId, newState)
    }
}
