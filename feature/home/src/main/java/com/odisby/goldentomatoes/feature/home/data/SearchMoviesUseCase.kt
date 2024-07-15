package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.discover.repositories.SearchMoviesRepository
import com.odisby.goldentomatoes.feature.home.model.SearchMovie
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val searchMoviesRepository: SearchMoviesRepository
) {
    suspend operator fun invoke(query: String): List<SearchMovie> {
        val result = searchMoviesRepository.searchMovies(query).map {
            SearchMovie(
                id = it.id,
                title = it.title,
                scheduled = it.scheduled
            )
        }

        return result.sortedByDescending { it.scheduled }
    }
}

/*
return discoverRepository.getDiscoverMovies().let {
            when (it) {
                is Resource.Success -> Resource.Success(it.data.map { it.toMovie() })
                is Resource.Error -> Resource.Error(it.message)
            }
        }
 */