package com.odisby.goldentomatoes.data.discover.remote.model

import com.google.gson.annotations.SerializedName

data class DiscoverMoviesRemote(
    val page: Int,
    val results: List<MoviesRemote>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)

data class MoviesRemote(
    val id: Long,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
)