package com.odisby.goldentomatoes.data.remote.source

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.DiscoverMoviesRemote
import com.odisby.goldentomatoes.data.data.model.MovieRemote
import com.odisby.goldentomatoes.data.data.source.DiscoverSource
import com.odisby.goldentomatoes.data.remote.api.DiscoverApi
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class DiscoverSourceRemote @Inject constructor(
    private val discoverApi: DiscoverApi
) : DiscoverSource.Remote {
    override suspend fun getDiscoverMovies(): Resource<List<MovieRemote>> {
        return try {
            val result = discoverApi.getDiscoverMovies()
            if (result.isSuccessful) {
                Resource.Success(result.body()?.results ?: emptyList())
            } else {
                Resource.Error(result.message())
            }
        } catch (e: Exception) {
            Timber.e("Failed to get discover movies from api. It get an exception. Message: ${e.message}")
            Resource.Error(e.message)
        }
    }

    override suspend fun getRandomMovie(page: Int): Response<DiscoverMoviesRemote> {
        return discoverApi.getRandomMovie(page)
    }
}
