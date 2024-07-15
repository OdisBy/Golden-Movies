package com.odisby.goldentomatoes.feature.movielist.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.movies.repositories.DiscoverRepository
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {
    suspend operator fun invoke(): Resource<List<MovieListItem>> {
        return discoverRepository.getDiscoverMovies().let {
            when (it) {
                is Resource.Success -> Resource.Success(it.data.map { it.toMovieListItem() })
                is Resource.Error -> Resource.Error(it.message)
            }
        }
    }
}