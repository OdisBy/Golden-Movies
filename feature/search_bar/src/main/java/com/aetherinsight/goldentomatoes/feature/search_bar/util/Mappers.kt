package com.aetherinsight.goldentomatoes.feature.search_bar.util

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.core.data.model.SearchMovie

fun SearchMovie.toMovieGlobal(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.overview,
        posterPath = this.posterPath,
        scheduled = false,
        favorite = this.favorite,
    )
}