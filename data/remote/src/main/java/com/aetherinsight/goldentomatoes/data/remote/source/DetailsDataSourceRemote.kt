package com.aetherinsight.goldentomatoes.data.remote.source

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.model.MovieRemote
import com.aetherinsight.goldentomatoes.data.data.source.DetailsDataSource
import com.aetherinsight.goldentomatoes.data.remote.api.DetailsApi
import timber.log.Timber
import javax.inject.Inject

class DetailsDataSourceRemote @Inject constructor(
    private val detailsApi: DetailsApi
) : DetailsDataSource.Remote {
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