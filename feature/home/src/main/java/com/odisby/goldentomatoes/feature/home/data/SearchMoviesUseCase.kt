package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.data.repositories.SearchMoviesRepositoryNew
import com.odisby.goldentomatoes.feature.home.model.SearchMovie
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val searchMoviesRepository: SearchMoviesRepositoryNew
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
