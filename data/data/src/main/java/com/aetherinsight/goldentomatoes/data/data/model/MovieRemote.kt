package com.aetherinsight.goldentomatoes.data.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieRemote(
    val id: Long,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
)
