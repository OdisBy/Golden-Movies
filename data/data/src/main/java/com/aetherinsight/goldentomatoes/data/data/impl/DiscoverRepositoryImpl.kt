package com.aetherinsight.goldentomatoes.data.data.impl

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.repositories.DiscoverRepository
import com.aetherinsight.goldentomatoes.data.data.source.DiscoverSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val remoteSource: DiscoverSource.Remote
) : DiscoverRepository {
    override suspend fun getDiscoverMovies(): Flow<Resource<List<MovieGlobal>>> = flow {
        emit(remoteSource.getDiscoverMovies().toMovieGlobal())
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
