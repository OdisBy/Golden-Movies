package com.odisby.goldentomatoes.data.discover.local.repositories

import com.odisby.goldentomatoes.data.discover.local.db.ScheduledMoviesDatabase
import com.odisby.goldentomatoes.data.discover.local.model.MovieEntity
import com.odisby.goldentomatoes.data.discover.repositories.ScheduledRepository
import javax.inject.Inject

internal class ScheduledRepositoryImpl @Inject constructor(
    db: ScheduledMoviesDatabase
) : ScheduledRepository {
    private val dao = db.getMoviesSchedulesDao()

    override suspend fun getScheduledMovies(): List<MovieEntity> {
        return dao.getQuantity(5)
    }

    override suspend fun addScheduledMovie(movie: MovieEntity) {
        dao.insert(movie)
    }
}
