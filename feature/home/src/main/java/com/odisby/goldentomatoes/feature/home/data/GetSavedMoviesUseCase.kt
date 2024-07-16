package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.data.repositories.SavedRepository
import com.odisby.goldentomatoes.feature.home.model.HomeMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSavedMoviesUseCase @Inject constructor(
    private val savedRepository: SavedRepository
) {
    suspend operator fun invoke(): Flow<List<HomeMovie>> =
        savedRepository.getSavedMovies().map { listMovies ->
            listMovies.map { movieGlobal ->
                movieGlobal.toMovie()
            }
        }
}
