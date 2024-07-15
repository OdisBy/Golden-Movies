package com.odisby.goldentomatoes.feature.details.data

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.feature.details.model.Movie

fun MovieGlobal.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        scheduled = this.scheduled
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        posterUrl = this.posterPath
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterUrl,
        scheduled = true
    )
}

fun Movie.toGlobalMovie(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        scheduled = true
    )
}
