package com.odisby.goldentomatoes.data.movies.local.repositories

import com.odisby.goldentomatoes.data.movies.local.db.ScheduledMoviesDatabase
import com.odisby.goldentomatoes.data.movies.local.model.MovieEntity
import com.odisby.goldentomatoes.data.movies.repositories.ScheduledRepository
import javax.inject.Inject

internal class ScheduledRepositoryImpl @Inject constructor(
    db: ScheduledMoviesDatabase
) : ScheduledRepository {
    private val dao = db.getMoviesSchedulesDao()

    private val scheduledMovies = mutableListOf<MovieEntity>()

    override suspend fun getScheduledMovies(): List<MovieEntity> {
        if (scheduledMovies.isEmpty()) {
            val movies = dao.getQuantity(5)
            scheduledMovies.addAll(movies)
        }
        return scheduledMovies
    }

    override suspend fun addScheduledMovie(movie: MovieEntity) {
        dao.insert(movie)
        scheduledMovies.add(movie)
    }

    override suspend fun removeScheduledMovie(movieId: Long) {
        dao.deleteById(movieId)
        scheduledMovies.removeIf { it.id == movieId }
    }

    override suspend fun getMoviesById(movieId: Long): MovieEntity? {
        val cacheMovie = scheduledMovies.find { it.id == movieId }
        if (cacheMovie != null) return cacheMovie
        return dao.getById(movieId)
    }
}
