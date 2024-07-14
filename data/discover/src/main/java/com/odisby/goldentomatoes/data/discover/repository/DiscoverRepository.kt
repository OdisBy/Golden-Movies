package com.odisby.goldentomatoes.data.discover.repository

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.discover.model.DiscoverMoviesRemote
import com.odisby.goldentomatoes.data.discover.model.MoviesRemote


interface DiscoverRepository {
    suspend fun getDiscoverMovies(): Resource<List<MoviesRemote>>
}