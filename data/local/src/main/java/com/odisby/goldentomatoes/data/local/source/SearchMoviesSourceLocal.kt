package com.odisby.goldentomatoes.data.local.source

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.source.SearchMoviesSource
import com.odisby.goldentomatoes.data.local.db.ScheduledMoviesDatabase
import javax.inject.Inject

class SearchMoviesSourceLocal @Inject constructor(
    db: ScheduledMoviesDatabase
) : SearchMoviesSource.Local {

    private val dao = db.getMoviesSchedulesDao()

    override suspend fun searchMovies(movieIds: List<Long>): List<MovieEntity?> {
        return movieIds.map {
            dao.getById(it)
        }
    }
}