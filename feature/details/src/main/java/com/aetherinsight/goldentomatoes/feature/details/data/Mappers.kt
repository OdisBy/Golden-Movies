package com.aetherinsight.goldentomatoes.feature.details.data

import com.aetherinsight.goldentomatoes.data.data.model.MovieEntity
import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.feature.details.model.MovieDetails

fun MovieGlobal.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        favorite = this.favorite,
        scheduled = this.scheduled
    )
}

fun MovieDetails.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        posterUrl = this.posterPath,
        scheduled = this.scheduled
    )
}

fun MovieEntity.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterUrl,
        favorite = true,
        scheduled = this.scheduled
    )
}

fun MovieDetails.toGlobalMovie(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        favorite = this.favorite,
        scheduled = this.scheduled
    )
}
