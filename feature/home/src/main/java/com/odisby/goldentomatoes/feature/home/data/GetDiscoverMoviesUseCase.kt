package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.discover.model.MoviesRemote
import com.odisby.goldentomatoes.data.discover.repository.DiscoverRepository
import com.odisby.goldentomatoes.feature.home.model.Movies
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {
    suspend operator fun invoke(): Resource<List<Movies>> {
        return discoverRepository.getDiscoverMovies().let {
            when (it) {
                is Resource.Success -> Resource.Success(it.data.map { it.toMovie() })
                is Resource.Error -> Resource.Error(it.message)
            }
        }
    }
}

fun MoviesRemote.toMovie(): Movies {
    return Movies(
        id = this.id,
        title = this.title,
        description = this.overview,
        posterPath = this.posterPath
    )
}
