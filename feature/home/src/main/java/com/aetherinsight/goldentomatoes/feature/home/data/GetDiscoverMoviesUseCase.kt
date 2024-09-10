package com.aetherinsight.goldentomatoes.feature.home.data

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.core.network.model.mapList
import com.aetherinsight.goldentomatoes.data.data.repositories.DiscoverRepository
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {
    operator fun invoke(): Flow<Resource<List<HomeMovie>>> =
        discoverRepository.getDiscoverMovies().map { resourceMovies ->
            resourceMovies.mapList { it.toHomeMovie() }
        }
}
