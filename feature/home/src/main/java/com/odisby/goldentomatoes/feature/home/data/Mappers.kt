package com.odisby.goldentomatoes.feature.home.data

import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.feature.home.model.HomeMovie

fun MovieGlobal.toMovie(): HomeMovie {
    return HomeMovie(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        saved = this.saved,
    )
}
