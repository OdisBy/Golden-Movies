package com.aetherinsight.goldentomatoes.data.data.source

import com.aetherinsight.goldentomatoes.data.data.model.MovieEntity
import com.aetherinsight.goldentomatoes.data.data.model.MovieRemote

interface SearchMoviesSource {
    interface Remote {
        suspend fun searchMovies(query: String): List<MovieRemote>
    }

    interface Local {
        suspend fun searchMovies(movieIds: List<Long>): List<MovieEntity?>
    }
}
