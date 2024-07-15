package com.odisby.goldentomatoes.data.movies.remote.model

import com.google.gson.annotations.SerializedName

data class DiscoverMoviesRemote(
    val page: Int,
    val results: List<MovieRemote>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)

data class MovieRemote(
    val id: Long,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
)