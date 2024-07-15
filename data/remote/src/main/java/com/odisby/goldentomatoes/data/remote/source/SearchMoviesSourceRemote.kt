package com.odisby.goldentomatoes.data.remote.source

import com.odisby.goldentomatoes.data.data.model.MovieRemote
import com.odisby.goldentomatoes.data.data.source.SearchMoviesSource
import com.odisby.goldentomatoes.data.remote.api.SearchApi
import timber.log.Timber
import javax.inject.Inject

class SearchMoviesSourceRemote @Inject constructor(
    private val searchApi: SearchApi
) : SearchMoviesSource.Remote {
    override suspend fun searchMovies(query: String): List<MovieRemote> {
        return try {
            val result = searchApi.searchMovie(query)
            if (!result.isSuccessful) {
                return emptyList()
            }
            return result.body()?.results ?: emptyList()
        } catch (e: Exception) {
            Timber.e(e)
            emptyList()
        }
    }
}
