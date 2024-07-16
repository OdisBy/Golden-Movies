package com.odisby.goldentomatoes.feature.movielist.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.core.ui.constants.ListTypes
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepository
import com.odisby.goldentomatoes.data.data.repositories.ScheduledRepository
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository,
    private val scheduledRepository: ScheduledRepository,
) {
    suspend operator fun invoke(type: ListTypes): Flow<Resource<List<MovieListItem>>> {
        return when (type) {
            ListTypes.DISCOVER -> getDiscoverMovies()
            ListTypes.SCHEDULED -> getScheduledMovies()
        }
    }

    private suspend fun getDiscoverMovies(): Flow<Resource<List<MovieListItem>>> = flow {
        discoverRepository.getDiscoverMovies().map {
            when (it) {
                is Resource.Success -> emit(Resource.Success(it.data.map { it.toMovieListItem() }))
                is Resource.Error -> emit(Resource.Error(it.message))
            }
        }
    }

    private suspend fun getScheduledMovies(): Flow<Resource<List<MovieListItem>>> = flow {
        val result = scheduledRepository.getScheduledMovies()

        emit(Resource.Success(result.map { it.toMovieListItem() }))
    }
}
