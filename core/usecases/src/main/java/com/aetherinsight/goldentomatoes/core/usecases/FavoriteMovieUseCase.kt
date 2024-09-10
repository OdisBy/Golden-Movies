package com.aetherinsight.goldentomatoes.core.usecases

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import javax.inject.Inject

class FavoriteMovieUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,

    ) {
    suspend operator fun invoke(movieGlobal: MovieGlobal) {
        if (movieGlobal.favorite) {
            favoriteRepository.removeFavoriteMovie(movieGlobal.id)
        } else {
            favoriteRepository.addFavoriteMovie(movieGlobal)
        }
    }
}
