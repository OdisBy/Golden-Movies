package com.odisby.goldentomatoes.data.data.impl

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.model.MovieRemote
import com.odisby.goldentomatoes.data.data.repositories.DetailsRepository
import com.odisby.goldentomatoes.data.data.source.DetailsDataSource
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DetailsDataSource.Remote,
    private val localDataSource: DetailsDataSource.Local
) : DetailsRepository {
    override suspend fun getMovieDetails(movieId: Long): Resource<MovieGlobal> {
        val localMovie = localDataSource.getMovieDetails(movieId)
        if (localMovie != null) {
            return Resource.Success(localMovie.toMovieGlobal())
        }

        val remoteMovie = remoteDataSource.getMovieDetails(movieId)
        return remoteMovie.toMovieGlobal()
    }
}

fun Resource<MovieRemote>.toMovieGlobal(): Resource<MovieGlobal> {
    return when (this) {
        is Resource.Success -> Resource.Success(this.data.toMovieGlobal())
        is Resource.Error -> Resource.Error(this.message)
    }
}
