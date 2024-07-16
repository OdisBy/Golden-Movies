package com.odisby.goldentomatoes.data.local.source

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.source.SavedMoviesSource
import com.odisby.goldentomatoes.data.local.db.SavedMoviesDatabase
import javax.inject.Inject

class SavedMoviesSourceLocal @Inject constructor(
    db: SavedMoviesDatabase
) : SavedMoviesSource.Local {

    private val dao = db.getMoviesSavedDao()

    override suspend fun getSavedMovies(): List<MovieGlobal> {
        return dao.getQuantity(5).toMovieGlobal()
    }

    override suspend fun addSavedMovie(movie: MovieGlobal) {
        dao.insert(movie.toMovieEntity())
    }

    override suspend fun removeSavedMovie(movieId: Long) {
        dao.deleteById(movieId)
    }

    override suspend fun getMoviesById(movieId: Long): MovieGlobal? {
        return dao.getById(movieId)?.toMovieGlobal()
    }

    override suspend fun setScheduledStatus(movieId: Long, newState: Boolean) {
        val movie = dao.getById(movieId) ?: return
        dao.update(movie.copy(scheduled = newState))
    }

}

private fun List<MovieEntity>.toMovieGlobal(): List<MovieGlobal> {
    return this.map { movie ->
        movie.toMovieGlobal()
    }
}

private fun MovieGlobal.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        posterUrl = this.posterPath,
        scheduled = this.scheduled
    )
}

private fun MovieEntity.toMovieGlobal(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterUrl,
        saved = true,
        scheduled = this.scheduled
    )
}
