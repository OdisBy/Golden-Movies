package com.aetherinsight.goldentomatoes.feature.details.data

import com.aetherinsight.goldentomatoes.data.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.feature.details.model.MovieDetails
import javax.inject.Inject

class SaveMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movieDetails: MovieDetails) {
        if (movieDetails.favorite) {
            favoriteRepository.removeFavoriteMovie(movieDetails.id)
        } else {
            favoriteRepository.addFavoriteMovie(movieDetails.toMovieGlobal())
        }
    }
}

private fun MovieDetails.toMovieGlobal(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        favorite = this.favorite,
        scheduled = this.scheduled
    )
}

