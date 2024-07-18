package com.odisby.goldentomatoes.feature.movielist.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.core.ui.constants.ListTypes
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepository
import com.odisby.goldentomatoes.data.data.repositories.FavoriteRepository
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(type: ListTypes): Flow<Resource<List<MovieListItem>>> {
        return when (type) {
            ListTypes.DISCOVER -> getDiscoverMovies()
            ListTypes.FAVORITE -> getFavoriteMovies()
        }
    }

    private suspend fun getDiscoverMovies(): Flow<Resource<List<MovieListItem>>> {
        return discoverRepository.getDiscoverMovies().map {
            when (it) {
                is Resource.Success -> Resource.Success(it.data.map { it.toMovieListItem() })
                is Resource.Error -> Resource.Error(it.message)
            }
        }
    }

    private suspend fun getFavoriteMovies(): Flow<Resource<List<MovieListItem>>> {
        return favoriteRepository.getFavoriteMovies().map {
            Resource.Success(it.map { it.toMovieListItem() })
        }
    }
}
