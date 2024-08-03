package com.aetherinsight.goldentomatoes.feature.movielist.data

import com.aetherinsight.goldentomatoes.data.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.feature.movielist.model.MovieListItem

fun MovieGlobal.toMovieListItem(): MovieListItem {
    return MovieListItem(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath
    )
}
