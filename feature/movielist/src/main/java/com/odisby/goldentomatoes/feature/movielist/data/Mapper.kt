package com.odisby.goldentomatoes.feature.movielist.data

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.model.MovieRemote
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem

fun MovieGlobal.toMovieListItem(): MovieListItem {
    return MovieListItem(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath
    )
}
