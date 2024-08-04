package com.aetherinsight.goldentomatoes.feature.home.data

import com.aetherinsight.goldentomatoes.data.data.repositories.SearchMoviesRepository
import com.aetherinsight.goldentomatoes.feature.home.model.SearchMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val searchMoviesRepository: SearchMoviesRepository
) {
    suspend operator fun invoke(query: String): Flow<List<SearchMovie>> =
        searchMoviesRepository.searchMovies(query).map { list ->
            list.map { movie ->
                SearchMovie(
                    id = movie.id,
                    title = movie.title,
                    favorite = movie.favorite
                )
            }
                .sortedByDescending { it.favorite }
        }
}