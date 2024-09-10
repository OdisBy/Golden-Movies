package com.aetherinsight.goldentomatoes.feature.home.data

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.core.network.model.mapList
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(): Flow<Resource<List<HomeMovie>>> =
        favoriteRepository.getFavoriteMovies()
            .map { resource ->
                resource.mapList { it.toHomeMovie() }
            }
}
