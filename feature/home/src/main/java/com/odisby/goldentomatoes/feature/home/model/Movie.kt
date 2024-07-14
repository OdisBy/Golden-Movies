package com.odisby.goldentomatoes.feature.home.model

data class Movie(
    val id: Long,
    val title: String,
    val description: String,
    val posterPath: String,
    val scheduled: Boolean = false,
)
