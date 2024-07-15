package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.movies.local.model.MovieEntity
import com.odisby.goldentomatoes.data.movies.remote.model.MovieRemote
import com.odisby.goldentomatoes.feature.home.model.Movie

fun MovieRemote.toMovie(): Movie {
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
        title = this.title,
        description = this.description,
        posterPath = this.posterUrl,
        scheduled = true
    )
}