package com.aetherinsight.goldentomatoes.data.data.impl

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.model.MovieRemote
import com.aetherinsight.goldentomatoes.data.data.repositories.DetailsRepository
import com.aetherinsight.goldentomatoes.data.data.source.DetailsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DetailsDataSource.Remote,
    private val localDataSource: DetailsDataSource.Local
) : DetailsRepository {

    override suspend fun getMovieDetails(movieId: Long): Flow<Resource<MovieGlobal>> = flow {
        try {
            val localMovie = localDataSource.getMovieDetails(movieId)

            if (localMovie != null) {
                emit(Resource.Success(localMovie.toMovieGlobal()))
            } else {
                val remoteMovie = remoteDataSource.getMovieDetails(movieId)
                emit(remoteMovie.toMovieGlobal())
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to fetch movie details"))
        }
    }
}


fun Resource<MovieRemote>.toMovieGlobal(): Resource<MovieGlobal> {
    return when (this) {
        is Resource.Success -> Resource.Success(this.data.toMovieGlobal())
        is Resource.Error -> Resource.Error(this.message)
    }
}
