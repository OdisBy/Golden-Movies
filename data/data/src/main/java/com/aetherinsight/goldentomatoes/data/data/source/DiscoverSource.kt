package com.aetherinsight.goldentomatoes.data.data.source

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.model.DiscoverMoviesRemote
import com.aetherinsight.goldentomatoes.data.data.model.MovieRemote
import retrofit2.Response

interface DiscoverSource {
    interface Remote : DiscoverSource {
        suspend fun getDiscoverMovies(): Resource<List<MovieRemote>>
        suspend fun getRandomMovie(page: Int): Response<DiscoverMoviesRemote>
    }
}
