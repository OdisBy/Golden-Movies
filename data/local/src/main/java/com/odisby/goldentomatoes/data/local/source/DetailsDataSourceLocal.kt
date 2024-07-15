package com.odisby.goldentomatoes.data.local.source

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.source.DetailsDataSource
import com.odisby.goldentomatoes.data.local.db.ScheduledMoviesDatabase
import javax.inject.Inject

class DetailsDataSourceLocal @Inject constructor(
    db: ScheduledMoviesDatabase
) : DetailsDataSource.Local {

    private val dao = db.getMoviesSchedulesDao()

    override suspend fun getMovieDetails(movieId: Long): MovieEntity? {
        return dao.getById(movieId)
    }
}