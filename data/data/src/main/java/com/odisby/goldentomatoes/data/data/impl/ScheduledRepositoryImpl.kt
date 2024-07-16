package com.odisby.goldentomatoes.data.data.impl

import android.util.Log
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.repositories.ScheduledRepository
import com.odisby.goldentomatoes.data.data.source.ScheduledMoviesSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduledRepositoryImpl @Inject constructor(
    private val localDataSource: ScheduledMoviesSource.Local
) : ScheduledRepository {

    private val scheduledMovies = mutableListOf<MovieGlobal>()

    override suspend fun getScheduledMovies(): Flow<List<MovieGlobal>> = flow {
        if (scheduledMovies.isEmpty()) {
            val movies = localDataSource.getScheduledMovies()
            scheduledMovies.addAll(movies)
        }

        emit(scheduledMovies)
    }

    override suspend fun addScheduledMovie(movie: MovieGlobal) {
        localDataSource.addScheduledMovie(movie)
        scheduledMovies.add(movie)
    }

    override suspend fun removeScheduledMovie(movieId: Long) {
        localDataSource.removeScheduledMovie(movieId)
        scheduledMovies.removeIf { it.id == movieId }
    }

    override suspend fun getMoviesById(movieId: Long): Flow<MovieGlobal?> = flow {
        val cacheMovie = scheduledMovies.find { it.id == movieId }
        if (cacheMovie != null) emit(cacheMovie)

        emit(localDataSource.getMoviesById(movieId))
    }
}
