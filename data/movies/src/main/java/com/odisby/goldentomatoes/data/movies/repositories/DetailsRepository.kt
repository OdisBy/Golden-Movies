package com.odisby.goldentomatoes.data.movies.repositories

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.movies.remote.model.MovieRemote

interface DetailsRepository {
    suspend fun getMovieDetails(movieId: Long): Resource<MovieRemote>
}
