package com.aetherinsight.goldentomatoes.feature.search_bar.data

import com.aetherinsight.goldentomatoes.core.data.model.SearchMovie
import com.aetherinsight.goldentomatoes.data.data.repositories.SearchMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val searchMoviesRepository: SearchMoviesRepository
) {
    suspend operator fun invoke(query: String): Flow<List<SearchMovie>> =
        searchMoviesRepository.searchMovies(query).map { list ->
            list.sortedByDescending { it.favorite }
        }
}
