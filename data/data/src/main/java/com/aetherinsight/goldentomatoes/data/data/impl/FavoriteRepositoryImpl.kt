package com.aetherinsight.goldentomatoes.data.data.impl

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.data.data.source.FavoriteMoviesSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteMoviesSource.Local
) : FavoriteRepository {

    private val cache = mutableListOf<MovieGlobal>()

    override fun getFavoriteMovies(): Flow<Resource<List<MovieGlobal>>> = flow {
        emit(Resource.Loading())

        localDataSource.getFavoriteMovies()
            .onStart {
                val movies = localDataSource.getFavoriteMovies().firstOrNull() ?: emptyList()
                cache.clear()
                cache.addAll(movies)
                emit(Resource.Success(movies))
            }
            .distinctUntilChanged()
            .catch {
                emit(Resource.Error(it.localizedMessage ?: "Error"))
            }
            .collect { movies ->
                cache.clear()
                cache.addAll(movies)
                emit(Resource.Success(movies))
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
