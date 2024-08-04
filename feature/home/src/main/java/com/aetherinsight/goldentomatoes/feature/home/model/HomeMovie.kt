package com.aetherinsight.goldentomatoes.feature.home.model

data class HomeMovie(
    val id: Long,
    val title: String,
    val description: String,
    val posterPath: String,
    val favorite: Boolean,
)
