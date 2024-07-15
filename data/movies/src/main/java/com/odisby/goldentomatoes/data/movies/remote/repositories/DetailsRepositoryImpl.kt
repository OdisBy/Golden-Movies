package com.odisby.goldentomatoes.data.movies.remote.repositories

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.movies.remote.api.DetailsApi
import com.odisby.goldentomatoes.data.movies.remote.model.MovieRemote
import com.odisby.goldentomatoes.data.movies.repositories.DetailsRepository
import timber.log.Timber

internal class DetailsRepositoryImpl(
    private val detailsApi: DetailsApi
) : DetailsRepository {
    override suspend fun getMovieDetails(movieId: Long): Resource<MovieRemote> {
        return try {
            val result = detailsApi.getMovieDetails(movieId)

            if (result.isSuccessful) {
                Resource.Success(result.body()!!)
            } else {
                Resource.Error(result.message())
            }
        } catch (e: Exception) {
            Timber.e("Failed to get movie details from api. It get an exception. Message: ${e.message}")
            Resource.Error(e.message)
        }
    }
}
