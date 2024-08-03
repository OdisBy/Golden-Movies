package com.aetherinsight.goldentomatoes.data.local.source

import com.aetherinsight.goldentomatoes.data.data.model.MovieEntity
import com.aetherinsight.goldentomatoes.data.data.source.SearchMoviesSource
import com.aetherinsight.goldentomatoes.data.local.db.FavoriteMoviesDatabase
import javax.inject.Inject

class SearchMoviesSourceLocal @Inject constructor(
    db: FavoriteMoviesDatabase
) : SearchMoviesSource.Local {

    private val dao = db.getMoviesFavoriteDao()

    override suspend fun searchMovies(movieIds: List<Long>): List<MovieEntity?> {
        return movieIds.map {
            dao.getById(it)
        }
    }
}