package com.odisby.goldentomatoes.data.movies.remote.repositories

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.movies.remote.api.DiscoverApi
import com.odisby.goldentomatoes.data.movies.remote.model.MovieRemote
import com.odisby.goldentomatoes.data.movies.repositories.DiscoverRepository
import timber.log.Timber

internal class DiscoverRepositoryImpl(
    private val discoverApi: DiscoverApi
) : DiscoverRepository {
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
}