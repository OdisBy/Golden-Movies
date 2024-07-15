package com.odisby.goldentomatoes.data.data.repositories

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.MovieGlobal

interface DetailsRepositoryNew {
    suspend fun getMovieDetails(movieId: Long): Resource<MovieGlobal>
}
