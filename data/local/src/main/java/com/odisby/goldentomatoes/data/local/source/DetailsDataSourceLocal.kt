package com.odisby.goldentomatoes.data.local.source

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.source.DetailsDataSource
import com.odisby.goldentomatoes.data.local.db.FavoriteMoviesDatabase
import javax.inject.Inject

class DetailsDataSourceLocal @Inject constructor(
    db: FavoriteMoviesDatabase
) : DetailsDataSource.Local {

    private val dao = db.getMoviesFavoriteDao()

    override suspend fun getMovieDetails(movieId: Long): MovieEntity? {
        return dao.getById(movieId)
    }
}
