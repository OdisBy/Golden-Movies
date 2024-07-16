package com.odisby.goldentomatoes.data.data.impl

import com.odisby.goldentomatoes.data.data.model.SearchMovieRemote
import com.odisby.goldentomatoes.data.data.repositories.SearchMoviesRepository
import com.odisby.goldentomatoes.data.data.source.SearchMoviesSource
import timber.log.Timber
import javax.inject.Inject

class SearchMoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchMoviesSource.Remote,
    private val localDataSource: SearchMoviesSource.Local
) : SearchMoviesRepository {
    override suspend fun searchMovies(query: String): List<SearchMovieRemote> {
        return try {
            val resultRemote = remoteDataSource.searchMovies(query)

            // It wont filter by local, just check if the remotes ones are in the local ones
            val resultLocal = localDataSource.searchMovies(resultRemote.map { it.id })

            return resultRemote.map { movieRemote ->
                SearchMovieRemote(
                    id = movieRemote.id,
                    title = movieRemote.title,
                    scheduled = resultLocal.any { it?.id == movieRemote.id }
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
            emptyList()
        }
    }
}