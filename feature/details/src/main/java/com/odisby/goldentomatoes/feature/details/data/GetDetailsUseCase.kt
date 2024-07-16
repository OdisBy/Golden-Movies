package com.odisby.goldentomatoes.feature.details.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.repositories.DetailsRepository
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepository
import com.odisby.goldentomatoes.feature.details.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val discoverRepository: DiscoverRepository,
) {
    suspend operator fun invoke(movieId: Long): Flow<Resource<MovieDetails>> =
        detailsRepository.getMovieDetails(movieId).map {
            when (it) {
                is Resource.Success -> Resource.Success(it.data.toMovieDetails())
                is Resource.Error -> Resource.Error(it.message)
            }
        }

    suspend fun getRandomMovieDetails(): Flow<Resource<MovieDetails>> = flow {
        discoverRepository.randomMovieId()?.let { movieId ->
            invoke(movieId).collect { emit(it) }
        } ?: emit(Resource.Error("Cannot get random movie ID"))
    }
}
