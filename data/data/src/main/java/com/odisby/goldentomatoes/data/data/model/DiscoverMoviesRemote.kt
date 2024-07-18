package com.odisby.goldentomatoes.data.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DiscoverMoviesRemote(
    val page: Int,
    val results: List<MovieRemote>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
