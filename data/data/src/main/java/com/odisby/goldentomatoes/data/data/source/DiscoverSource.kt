package com.odisby.goldentomatoes.data.data.source

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.DiscoverMoviesRemote
import com.odisby.goldentomatoes.data.data.model.MovieRemote
import retrofit2.Response

interface DiscoverSource {
    interface Remote : DiscoverSource {
        suspend fun getDiscoverMovies(): Resource<List<MovieRemote>>
        suspend fun getRandomMovie(page: Int): Response<DiscoverMoviesRemote>
    }
}
