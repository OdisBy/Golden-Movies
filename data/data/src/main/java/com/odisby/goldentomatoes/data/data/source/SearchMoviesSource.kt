package com.odisby.goldentomatoes.data.data.source

import com.odisby.goldentomatoes.data.data.model.MovieEntity
import com.odisby.goldentomatoes.data.data.model.MovieRemote

interface SearchMoviesSource {
    interface Remote {
        suspend fun searchMovies(query: String): List<MovieRemote>
    }

    interface Local {
        suspend fun searchMovies(movieIds: List<Long>): List<MovieEntity?>
    }
}
