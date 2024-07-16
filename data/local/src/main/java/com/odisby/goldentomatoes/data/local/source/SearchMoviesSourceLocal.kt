package com.odisby.goldentomatoes.data.local.source

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.source.SearchMoviesSource
import com.odisby.goldentomatoes.data.local.db.SavedMoviesDatabase
import javax.inject.Inject

class SearchMoviesSourceLocal @Inject constructor(
    db: SavedMoviesDatabase
) : SearchMoviesSource.Local {

    private val dao = db.getMoviesSavedDao()

    override suspend fun searchMovies(movieIds: List<Long>): List<MovieEntity?> {
        return movieIds.map {
            dao.getById(it)
        }
    }
}