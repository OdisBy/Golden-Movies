package com.odisby.goldentomatoes.feature.details.model

data class MovieDetails(
    val id: Long,
    val title: String,
    val description: String,
    val posterPath: String,
    val saved: Boolean,
    val scheduled: Boolean
)
