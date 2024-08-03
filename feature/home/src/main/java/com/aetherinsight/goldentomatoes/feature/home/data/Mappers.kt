package com.aetherinsight.goldentomatoes.feature.home.data

import com.aetherinsight.goldentomatoes.data.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie

fun MovieGlobal.toMovie(): HomeMovie {
    return HomeMovie(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterPath,
        favorite = this.favorite,
    )
}
