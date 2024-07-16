package com.odisby.goldentomatoes.data.data.model

data class MovieGlobal(
    val id: Long,
    val title: String,
    val description: String,
    val posterPath: String,
    val saved: Boolean,
    val scheduled: Boolean,
)