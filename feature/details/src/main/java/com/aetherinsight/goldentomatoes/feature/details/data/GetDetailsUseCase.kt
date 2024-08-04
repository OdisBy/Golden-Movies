package com.aetherinsight.goldentomatoes.feature.details.data

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.repositories.DetailsRepository
import com.aetherinsight.goldentomatoes.data.data.repositories.DiscoverRepository
import com.aetherinsight.goldentomatoes.feature.details.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val discoverRepository: DiscoverRepository,
) {
    suspend operator fun invoke(movieId: Long): Flow<Resource<MovieDetails>> =
        detailsRepository.getMovieDetails(movieId).map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data.toMovieDetails())
                is Resource.Error -> Resource.Error(resource.message)
            }
        }

    suspend fun getRandomMovieDetails(): Flow<Resource<MovieDetails>> = flow {
        discoverRepository.randomMovieId()?.let { movieId ->
            invoke(movieId).collect { emit(it) }
        } ?: emit(Resource.Error("Cannot get random movie ID"))
    }
}