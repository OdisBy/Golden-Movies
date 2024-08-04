package com.aetherinsight.goldentomatoes.feature.details.model

data class MovieDetails(
    val id: Long,
    val title: String,
    val description: String,
    val posterPath: String,
    val favorite: Boolean,
    val scheduled: Boolean
)
