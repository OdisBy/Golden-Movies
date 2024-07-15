package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.data.repositories.ScheduledRepositoryNew
import com.odisby.goldentomatoes.feature.home.model.Movie
import javax.inject.Inject

class GetSchedulesMoviesUseCase @Inject constructor(
    private val scheduledMoviesRepository: ScheduledRepositoryNew
) {
    suspend operator fun invoke(): List<Movie> {
        return scheduledMoviesRepository.getScheduledMovies().map { it.toMovie() }
    }
}
