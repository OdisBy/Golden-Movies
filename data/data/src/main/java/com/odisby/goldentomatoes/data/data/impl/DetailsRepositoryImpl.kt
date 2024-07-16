package com.odisby.goldentomatoes.data.data.impl

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.model.MovieRemote
import com.odisby.goldentomatoes.data.data.repositories.DetailsRepository
import com.odisby.goldentomatoes.data.data.source.DetailsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DetailsDataSource.Remote,
    private val localDataSource: DetailsDataSource.Local
) : DetailsRepository {

    override suspend fun getMovieDetails(movieId: Long): Flow<Resource<MovieGlobal>> = flow {
        localDataSource.getMovieDetails(movieId)?.let { localMovie ->
            emit(Resource.Success(localMovie.toMovieGlobal()))
        }

        try {
            val remoteMovie = remoteDataSource.getMovieDetails(movieId)
            emit(remoteMovie.toMovieGlobal())
        } catch (e: Exception) {
            // Handle the exception and emit an error state
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
