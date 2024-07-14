package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.discover.repositories.ScheduledRepository
import com.odisby.goldentomatoes.feature.home.model.Movie
import javax.inject.Inject

class GetSchedulesMoviesUseCase @Inject constructor(
    private val scheduledMoviesRepository: ScheduledRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return scheduledMoviesRepository.getScheduledMovies().map { it.toMovie() }
    }
}
