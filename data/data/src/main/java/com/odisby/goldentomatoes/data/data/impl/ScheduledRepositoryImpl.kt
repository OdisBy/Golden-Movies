package com.odisby.goldentomatoes.data.data.impl

import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.repositories.ScheduledRepository
import com.odisby.goldentomatoes.data.data.source.ScheduledMoviesSource
import javax.inject.Inject

class ScheduledRepositoryImpl @Inject constructor(
    private val localDataSource: ScheduledMoviesSource.Local
) : ScheduledRepository {

    private val scheduledMovies = mutableListOf<MovieGlobal>()

    override suspend fun getScheduledMovies(): List<MovieGlobal> {
        if (scheduledMovies.isEmpty()) {
            val movies = localDataSource.getScheduledMovies()
            scheduledMovies.addAll(movies)
        }

        return scheduledMovies
    }

    override suspend fun addScheduledMovie(movie: MovieGlobal) {
        localDataSource.addScheduledMovie(movie)
        scheduledMovies.add(movie)
    }

    override suspend fun removeScheduledMovie(movieId: Long) {
        localDataSource.removeScheduledMovie(movieId)
        scheduledMovies.removeIf { it.id == movieId }
    }

    override suspend fun getMoviesById(movieId: Long): MovieGlobal? {
        val cacheMovie = scheduledMovies.find { it.id == movieId }
        if (cacheMovie != null) return cacheMovie

        return localDataSource.getMoviesById(movieId)
    }
}
