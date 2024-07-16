package com.odisby.goldentomatoes.data.local.source

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.source.DetailsDataSource
import com.odisby.goldentomatoes.data.local.db.SavedMoviesDatabase
import javax.inject.Inject

class DetailsDataSourceLocal @Inject constructor(
    db: SavedMoviesDatabase
) : DetailsDataSource.Local {

    private val dao = db.getMoviesSavedDao()

    override suspend fun getMovieDetails(movieId: Long): MovieEntity? {
        return dao.getById(movieId)
    }
}
