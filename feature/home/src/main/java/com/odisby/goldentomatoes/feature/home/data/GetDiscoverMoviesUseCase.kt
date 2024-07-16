package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepository
import com.odisby.goldentomatoes.feature.home.model.HomeMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<HomeMovie>>> =
        discoverRepository.getDiscoverMovies().map {
            when (it) {
                is Resource.Success -> Resource.Success(it.data.map { it.toMovie() })
                is Resource.Error -> Resource.Error(it.message)
            }
        }
}
