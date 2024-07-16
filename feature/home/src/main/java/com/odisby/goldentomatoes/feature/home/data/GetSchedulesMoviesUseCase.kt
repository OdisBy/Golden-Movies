package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.data.repositories.ScheduledRepository
import com.odisby.goldentomatoes.feature.home.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSchedulesMoviesUseCase @Inject constructor(
    private val scheduledMoviesRepository: ScheduledRepository
) {
    suspend operator fun invoke(): Flow<List<Movie>> =
        scheduledMoviesRepository.getScheduledMovies().map { listMovies ->
            listMovies.map { movieGlobal ->
                movieGlobal.toMovie()
            }
        }
}
