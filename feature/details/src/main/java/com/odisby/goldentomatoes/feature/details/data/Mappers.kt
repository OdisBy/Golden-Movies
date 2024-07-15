package com.odisby.goldentomatoes.feature.details.data

import com.odisby.goldentomatoes.data.movies.remote.model.MovieRemote
import com.odisby.goldentomatoes.feature.details.model.Movie

fun MovieRemote.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        description = this.overview,
        posterPath = this.posterPath
    )
}