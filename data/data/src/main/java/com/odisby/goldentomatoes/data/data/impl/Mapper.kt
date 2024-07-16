package com.odisby.goldentomatoes.data.data.impl

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.model.MovieRemote

fun MovieRemote.toMovieGlobal(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.overview,
        posterPath = this.posterPath,
        saved = false,
        scheduled = false
    )
}

fun MovieEntity.toMovieGlobal(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterUrl,
        saved = true,
        scheduled = this.scheduled
    )
}

fun Resource<List<MovieRemote>>.toMovieGlobal(): Resource<List<MovieGlobal>> {
    return when (this) {
        is Resource.Success -> Resource.Success(data.map { it.toMovieGlobal() })
        is Resource.Error -> Resource.Error(message)
    }
}
