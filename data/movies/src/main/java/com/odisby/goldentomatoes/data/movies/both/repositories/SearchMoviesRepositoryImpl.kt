package com.odisby.goldentomatoes.data.movies.both.repositories

import com.odisby.goldentomatoes.data.movies.both.model.SearchMovieRemote
import com.odisby.goldentomatoes.data.movies.local.db.ScheduledMoviesDatabase
import com.odisby.goldentomatoes.data.movies.remote.api.SearchApi
import com.odisby.goldentomatoes.data.movies.repositories.SearchMoviesRepository
import timber.log.Timber
import javax.inject.Inject

internal class SearchMoviesRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    db: ScheduledMoviesDatabase
) : SearchMoviesRepository {

    private val dao = db.getMoviesSchedulesDao()

    override suspend fun searchMovies(query: String): List<SearchMovieRemote> {
        return try {
            val result = searchApi.searchMovie(query)

            if (!result.isSuccessful) {
                return emptyList()
            }

            val scheduledMovies = result.body()?.results?.map { movieRemote ->
                dao.getById(movieRemote.id)
            }

            val movies = result.body()?.results ?: emptyList()
            movies.map { movie ->
                SearchMovieRemote(
                    id = movie.id,
                    title = movie.title,
                    scheduled = scheduledMovies?.any { it?.id == movie.id } == true
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
            emptyList()
        }
    }
}

