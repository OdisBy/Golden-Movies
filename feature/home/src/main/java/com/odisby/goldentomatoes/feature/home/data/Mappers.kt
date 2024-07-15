package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.feature.home.model.Movie

fun MovieGlobal.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        scheduled = this.scheduled
    )
}
