package com.aetherinsight.goldentomatoes.core.data.model

data class SearchMovie(
    val id: Long,
    val title: String,
    val posterPath: String,
    val overview: String,
    val favorite: Boolean
)
