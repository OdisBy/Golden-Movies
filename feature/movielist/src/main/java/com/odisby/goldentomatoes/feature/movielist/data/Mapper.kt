package com.odisby.goldentomatoes.feature.movielist.data

import com.odisby.goldentomatoes.data.movies.local.model.MovieEntity
import com.odisby.goldentomatoes.data.movies.remote.model.MovieRemote
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem

fun MovieRemote.toMovieListItem(): MovieListItem {
    return MovieListItem(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath
    )
}

fun MovieEntity.toMovieListItem() = MovieListItem(
    id = this.id,
    title = this.name,
    posterPath = this.posterUrl
)
