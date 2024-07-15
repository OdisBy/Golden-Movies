package com.odisby.goldentomatoes.data.data.source

import com.odisby.goldentomatoes.data.data.model.MovieGlobal

interface ScheduledMoviesSource {
    interface Local : ScheduledMoviesSource {
        suspend fun getScheduledMovies(): List<MovieGlobal>
        suspend fun addScheduledMovie(movie: MovieGlobal)
        suspend fun removeScheduledMovie(movieId: Long)
        suspend fun getMoviesById(movieId: Long): MovieGlobal?
    }
}
