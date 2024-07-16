package com.odisby.goldentomatoes.feature.details.data

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.feature.details.model.MovieDetails

fun MovieGlobal.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        scheduled = this.scheduled
    )
}

fun MovieDetails.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        posterUrl = this.posterPath
    )
}

fun MovieEntity.toMovie(): MovieDetails {
    return MovieDetails(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterUrl,
        scheduled = true
    )
}

fun MovieDetails.toGlobalMovie(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        scheduled = true
    )
}
