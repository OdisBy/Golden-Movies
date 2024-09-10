package com.aetherinsight.goldentomatoes.core.data.model

data class MovieGlobal(
    val id: Long,
    val title: String,
    val description: String,
    val posterPath: String,
    val favorite: Boolean,
    val scheduled: Boolean,
)