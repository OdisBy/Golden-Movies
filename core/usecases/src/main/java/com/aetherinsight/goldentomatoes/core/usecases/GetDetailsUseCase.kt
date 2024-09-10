package com.aetherinsight.goldentomatoes.core.usecases

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.repositories.DetailsRepository
import com.aetherinsight.goldentomatoes.data.data.repositories.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val discoverRepository: DiscoverRepository,
) {
    suspend operator fun invoke(movieId: Long): Flow<Resource<MovieGlobal>> =
        detailsRepository.getMovieDetails(movieId).map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data)
                is Resource.Error -> Resource.Error(resource.message)
                is Resource.Loading -> Resource.Loading()
            }
        }

    fun getRandomMovieDetails(): Flow<Resource<MovieGlobal>> = flow {
        discoverRepository.randomMovieId()?.let { movieId ->
            invoke(movieId).collect { emit(it) }
        } ?: emit(Resource.Error("Cannot get random movie ID"))
    }
}
