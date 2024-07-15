package com.odisby.goldentomatoes.data.local.source

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.source.ScheduledMoviesSource
import com.odisby.goldentomatoes.data.local.db.ScheduledMoviesDatabase
import javax.inject.Inject

class ScheduledMoviesSourceLocal @Inject constructor(
    db: ScheduledMoviesDatabase
) : ScheduledMoviesSource.Local {

    private val dao = db.getMoviesSchedulesDao()

    override suspend fun getScheduledMovies(): List<MovieGlobal> {
        return dao.getQuantity(5).toMovieGlobal()
    }

    override suspend fun addScheduledMovie(movie: MovieGlobal) {
        dao.insert(movie.toMovieEntity())
    }

    override suspend fun removeScheduledMovie(movieId: Long) {
        dao.deleteById(movieId)
    }

    override suspend fun getMoviesById(movieId: Long): MovieGlobal? {
        return dao.getById(movieId)?.toMovieGlobal()
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
    )
}

private fun MovieEntity.toMovieGlobal(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterUrl,
        scheduled = true
    )
}