package com.odisby.goldentomatoes.feature.details.data

import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.repositories.SavedRepository
import com.odisby.goldentomatoes.feature.details.model.MovieDetails
import javax.inject.Inject

class SaveMoviesUseCase @Inject constructor(
    private val savedRepository: SavedRepository
) {
    suspend operator fun invoke(movieDetails: MovieDetails) {
        if (movieDetails.saved) {
            savedRepository.removeSavedMovie(movieDetails.id)
        } else {
            savedRepository.addSavedMovie(movieDetails.toMovieGlobal())
        }
    }
}

private fun MovieDetails.toMovieGlobal(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        saved = this.saved,
        scheduled = this.scheduled
    )
}

