package com.odisby.goldentomatoes.feature.movielist.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.core.ui.constants.ListTypes
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepository
import com.odisby.goldentomatoes.data.data.repositories.ScheduledRepository
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository,
    private val scheduledRepository: ScheduledRepository,
) {
    suspend operator fun invoke(type: ListTypes): Resource<List<MovieListItem>> {
        return when (type) {
            ListTypes.DISCOVER -> getDiscoverMovies()
            ListTypes.SCHEDULED -> getScheduledMovies()
        }
    }

    private suspend fun getDiscoverMovies(): Resource<List<MovieListItem>> {
        return discoverRepository.getDiscoverMovies().let {
            when (it) {
                is Resource.Success -> Resource.Success(it.data.map { it.toMovieListItem() })
                is Resource.Error -> Resource.Error(it.message)
            }
        }
    }

    private suspend fun getScheduledMovies(): Resource<List<MovieListItem>> {
        val result = scheduledRepository.getScheduledMovies()

        return Resource.Success(result.map { it.toMovieListItem() })
    }
}