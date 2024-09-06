package com.aetherinsight.goldentomatoes.data.data.impl

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.model.MovieEntity
import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.model.MovieRemote

fun MovieRemote.toMovieGlobal(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.overview,
        posterPath = this.posterPath ?: "",
        favorite = false,
        scheduled = false
    )
}

fun MovieEntity.toMovieGlobal(): MovieGlobal {
    return MovieGlobal(
        id = this.id,
        title = this.title,
        description = this.description,
        posterPath = this.posterUrl,
        favorite = true,
        scheduled = this.scheduled
    )
}

fun Resource<List<MovieRemote>>.toMovieGlobal(): Resource<List<MovieGlobal>> {
    return when (this) {
        is Resource.Success -> Resource.Success(data.map { it.toMovieGlobal() })
        is Resource.Error -> Resource.Error(message)
    }
}
