package com.aetherinsight.goldentomatoes.feature.movielist.data

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.core.network.model.mapList
import com.aetherinsight.goldentomatoes.core.ui.constants.ListTypes
import com.aetherinsight.goldentomatoes.data.data.repositories.DiscoverRepository
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.feature.movielist.model.MovieListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListMoviesUseCase @Inject constructor(
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
        return discoverRepository.getDiscoverMovies().map { resourceMovies ->
            when (resourceMovies) {
                is Resource.Success -> Resource.Success(resourceMovies.data.map { it.toMovieListItem() })
                is Resource.Error -> Resource.Error(resourceMovies.message)
                is Resource.Loading -> Resource.Loading()
            }
        }
    }

    private fun getFavoriteMovies(): Flow<Resource<List<MovieListItem>>> {
        return favoriteRepository.getFavoriteMovies().map { resource ->
            resource.mapList { it.toMovieListItem() }
        }
    }
}
