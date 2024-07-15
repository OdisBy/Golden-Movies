package com.odisby.goldentomatoes.data.movies.both.model

/*
 * Calling it remote because I'll use both remote and local database
 * And I want to declare a SearchMovie data class at UI level
 */
data class SearchMovieRemote(
    val id: Long,
    val title: String,
    val scheduled: Boolean,
)
