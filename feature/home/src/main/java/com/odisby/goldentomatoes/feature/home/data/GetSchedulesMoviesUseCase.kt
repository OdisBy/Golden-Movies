package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.data.repositories.SavedRepository
import com.odisby.goldentomatoes.feature.home.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSchedulesMoviesUseCase @Inject constructor(
    private val scheduledMoviesRepository: SavedRepository
) {
    suspend operator fun invoke(): Flow<List<Movie>> =
        scheduledMoviesRepository.getSavedMovies().map { listMovies ->
            listMovies.map { movieGlobal ->
                movieGlobal.toMovie()
            }
        }
}
