package com.odisby.goldentomatoes.data.data.impl

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepositoryNew
import com.odisby.goldentomatoes.data.data.source.DiscoverSource
import timber.log.Timber
import javax.inject.Inject

class DiscoverRepositoryNewImpl @Inject constructor(
    private val remoteSource: DiscoverSource.Remote
) : DiscoverRepositoryNew {
    override suspend fun getDiscoverMovies(): Resource<List<MovieGlobal>> {
        return remoteSource.getDiscoverMovies().toMovieGlobal()
    }

    override suspend fun randomMovieId(): Long? {
        return try {
            val page = (1..40).random()
            val itemIndex = (1..19).random()

            val result = remoteSource.getRandomMovie(page)

            if (result.isSuccessful) {
                return result.body()?.results?.get(itemIndex)?.id
            }
            null
        } catch (e: Exception) {
            Timber.e("Failed to get random movies id from api. It get an exception. Message: ${e.message}")
            null
        }
    }
}

