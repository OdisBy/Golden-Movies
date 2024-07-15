package com.odisby.goldentomatoes.feature.details.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.movies.repositories.DetailsRepository
import com.odisby.goldentomatoes.data.movies.repositories.DiscoverRepository
import com.odisby.goldentomatoes.feature.details.model.Movie
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val discoverRepository: DiscoverRepository,
) {
    suspend operator fun invoke(movieId: Long): Movie {
        return detailsRepository.getMovieDetails(movieId).let {
            when (it) {
                is Resource.Success -> it.data.toMovie()
                is Resource.Error -> throw Exception(it.message ?: "Error")
            }
        }
    }

    suspend fun randomMovieId(): Movie? {
        val id = discoverRepository.randomMovieId() ?: return null
        return invoke(id)
    }
}
