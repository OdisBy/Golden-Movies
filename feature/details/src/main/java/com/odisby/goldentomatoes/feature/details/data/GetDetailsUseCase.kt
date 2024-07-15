package com.odisby.goldentomatoes.feature.details.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.repositories.DetailsRepository
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepository
import com.odisby.goldentomatoes.feature.details.model.Movie
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val discoverRepository: DiscoverRepository,
) {
    suspend operator fun invoke(movieId: Long): Movie {
        return when (val result = detailsRepository.getMovieDetails(movieId)) {
            is Resource.Success -> result.data.toMovie()
            is Resource.Error -> throw Exception(result.message ?: "Error")
        }
    }

    suspend fun randomMovieId(): Movie? {
        val id = discoverRepository.randomMovieId() ?: return null
        return invoke(id)
    }
}
