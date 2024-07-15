package com.odisby.goldentomatoes.data.movies.repositories

import com.odisby.goldentomatoes.data.movies.local.model.MovieEntity

interface ScheduledRepository {
    suspend fun getScheduledMovies(): List<MovieEntity>

    suspend fun addScheduledMovie(movie: MovieEntity)
}