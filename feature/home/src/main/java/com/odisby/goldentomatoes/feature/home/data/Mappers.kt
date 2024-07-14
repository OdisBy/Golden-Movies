package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.discover.local.model.MovieEntity
import com.odisby.goldentomatoes.data.discover.remote.model.MoviesRemote
import com.odisby.goldentomatoes.feature.home.model.Movie

fun MoviesRemote.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        description = this.overview,
        posterPath = this.posterPath
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.name,
        description = this.description,
        posterPath = this.posterUrl
    )
}