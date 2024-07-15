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
