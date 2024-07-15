package com.odisby.goldentomatoes.data.movies.remote.model

import com.google.gson.annotations.SerializedName

data class MovieRemote(
    val id: Long,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
)
