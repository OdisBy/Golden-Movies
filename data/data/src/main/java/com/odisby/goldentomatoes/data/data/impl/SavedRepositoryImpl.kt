package com.odisby.goldentomatoes.data.data.impl

import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.repositories.SavedRepository
import com.odisby.goldentomatoes.data.data.source.SavedMoviesSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavedRepositoryImpl @Inject constructor(
    private val localDataSource: SavedMoviesSource.Local
) : SavedRepository {

    private val savedMovies = mutableListOf<MovieGlobal>()

    override suspend fun getSavedMovies(): Flow<List<MovieGlobal>> = flow {
        if (savedMovies.isEmpty()) {
            val movies = localDataSource.getSavedMovies()
            savedMovies.addAll(movies)
        }

        emit(savedMovies)
    }

    override suspend fun addSavedMovie(movie: MovieGlobal) {
        localDataSource.addSavedMovie(movie)
        savedMovies.add(movie)
    }

    override suspend fun removeSavedMovie(movieId: Long) {
        localDataSource.removeSavedMovie(movieId)
        savedMovies.removeIf { it.id == movieId }
    }

    override suspend fun getMoviesById(movieId: Long): Flow<MovieGlobal?> = flow {
        val cacheMovie = savedMovies.find { it.id == movieId }
        if (cacheMovie != null) emit(cacheMovie)

        emit(localDataSource.getMoviesById(movieId))
    }
}
