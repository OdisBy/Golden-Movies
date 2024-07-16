package com.odisby.goldentomatoes.data.data.source

import com.odisby.goldentomatoes.data.data.model.MovieGlobal

interface SavedMoviesSource {
    interface Local : SavedMoviesSource {
        suspend fun getSavedMovies(): List<MovieGlobal>
        suspend fun addSavedMovie(movie: MovieGlobal)
        suspend fun removeSavedMovie(movieId: Long)
        suspend fun getMoviesById(movieId: Long): MovieGlobal?
        suspend fun setScheduledStatus(movieId: Long, newState: Boolean)
    }
}
