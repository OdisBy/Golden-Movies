package com.aetherinsight.goldentomatoes.data.local.source

import com.aetherinsight.goldentomatoes.data.data.model.MovieEntity
import com.aetherinsight.goldentomatoes.data.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.source.FavoriteMoviesSource
import com.aetherinsight.goldentomatoes.data.local.db.FavoriteMoviesDatabase
import javax.inject.Inject

class FavoriteMoviesSourceLocal @Inject constructor(
    db: FavoriteMoviesDatabase
) : FavoriteMoviesSource.Local {

    private val dao = db.getMoviesFavoriteDao()

    override suspend fun getFavoriteMovies(): List<MovieGlobal> {
        return dao.getQuantity(5).toMovieGlobal()
    }

    override suspend fun addFavoriteMovie(movie: MovieGlobal) {
        dao.insert(movie.toMovieEntity())
    }

    override suspend fun removeFavoriteMovie(movieId: Long) {
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
        favorite = true,
        scheduled = this.scheduled
    )
}
