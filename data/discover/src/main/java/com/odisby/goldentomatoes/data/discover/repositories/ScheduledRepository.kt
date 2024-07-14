package com.odisby.goldentomatoes.data.discover.repositories

import com.odisby.goldentomatoes.data.discover.local.model.MovieEntity

interface ScheduledRepository {
    suspend fun getScheduledMovies(): List<MovieEntity>

    suspend fun addScheduledMovie(movie: MovieEntity)
}