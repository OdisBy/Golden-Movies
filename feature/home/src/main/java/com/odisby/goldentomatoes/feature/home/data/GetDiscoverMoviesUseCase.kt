package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.movies.repositories.DiscoverRepository
import com.odisby.goldentomatoes.feature.home.model.Movie
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {
    suspend operator fun invoke(): Resource<List<Movie>> {
        return discoverRepository.getDiscoverMovies().let {
            when (it) {
                is Resource.Success -> Resource.Success(it.data.map { it.toMovie() })
                is Resource.Error -> Resource.Error(it.message)
            }
        }
    }
}
