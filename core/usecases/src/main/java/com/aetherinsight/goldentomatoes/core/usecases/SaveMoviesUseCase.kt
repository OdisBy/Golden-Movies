package com.aetherinsight.goldentomatoes.core.usecases

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import javax.inject.Inject

class SaveMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movie: MovieGlobal) {
        if (movie.favorite) {
            favoriteRepository.removeFavoriteMovie(movie.id)
        } else {
            favoriteRepository.addFavoriteMovie(movie)
        }
    }
}
