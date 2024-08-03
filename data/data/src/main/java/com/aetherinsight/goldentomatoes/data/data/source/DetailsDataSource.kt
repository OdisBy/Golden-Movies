package com.aetherinsight.goldentomatoes.data.data.source

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.model.MovieEntity
import com.aetherinsight.goldentomatoes.data.data.model.MovieRemote

sealed interface DetailsDataSource {
    interface Remote : DetailsDataSource {
        suspend fun getMovieDetails(movieId: Long): Resource<MovieRemote>
    }

    interface Local : DetailsDataSource {
        suspend fun getMovieDetails(movieId: Long): MovieEntity?
    }
}