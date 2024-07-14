package com.odisby.goldentomatoes.data.discover.repositories

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.discover.remote.model.MoviesRemote


interface DiscoverRepository {
    suspend fun getDiscoverMovies(): Resource<List<MoviesRemote>>
}