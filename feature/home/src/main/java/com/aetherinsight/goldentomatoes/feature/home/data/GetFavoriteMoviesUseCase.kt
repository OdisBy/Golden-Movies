package com.aetherinsight.goldentomatoes.feature.home.data

import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<HomeMovie>> =
            favoriteRepository.getFavoriteMovies().map { listMovies ->
                listMovies.map { movieGlobal ->
                    movieGlobal.toHomeMovie()
                }
            }
}
