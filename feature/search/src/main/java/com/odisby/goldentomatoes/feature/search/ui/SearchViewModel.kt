package com.odisby.goldentomatoes.feature.search.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odisby.goldentomatoes.feature.search.model.Movie
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState: StateFlow<SearchScreenUiState> = _uiState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    private val moviesDumb = listOf(
        Movie(
            id = 1,
            name = "Inception",
            rating = null
        ),
        Movie(
            id = 2,
            name = "The Prestige",
            rating = null
        ),
        Movie(
            id = 3,
            name = "Interstellar",
            rating = null,
        ),
        Movie(
            id = 4,
            name = "Interworlds",
            rating = 9
        ),
        Movie(
            id = 5,
            name = "Intertest",
            rating = null
        )
    )

    init {
        initialiseUiState()
    }

    /*
     * With logic to get first movies with rating
     */
    private fun getMovies(searchQuery: String): List<Movie> {
        val moviesWithRating = mutableListOf<Movie>()
        val moviesWithoutRating = mutableListOf<Movie>()

        for (movie in moviesDumb) {
            if (movie.name.contains(searchQuery, ignoreCase = true)) {
                if (movie.rating != null) {
                    moviesWithRating.add(movie)
                } else {
                    moviesWithoutRating.add(movie)
                }
            }
        }
        moviesWithRating.sortByDescending { it.rating }
        return moviesWithRating + moviesWithoutRating
    }

    fun onQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    private fun initialiseUiState() {
        snapshotFlow { searchQuery }
            .debounce(500)
            .onEach { query ->
                if (query.length > 3) {
                    _uiState.update {
                        it.copy(isSearching = true)
                    }

                    val getMovies = viewModelScope.runCatching {
                        delay(2000)
                        getMovies(query)
                    }.getOrElse {
                        emptyList()
                    }

                    val result = getMovies.toPersistentList()

                    _uiState.update {
                        it.copy(
                            moviesList = result,
                            queryHasNoResults = result.isEmpty(),
                            isSearching = false,
                            errorMessage = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            moviesList = persistentListOf(),
                            queryHasNoResults = false,
                            isSearching = false,
                            errorMessage = null
                        )
                    }
                }
            }
            .catch { throwable ->
                Log.d("SearchViewModel", "Error: ${throwable.message}")
                _uiState.update {
                    it.copy(
                        moviesList = persistentListOf(),
                        queryHasNoResults = false,
                        isSearching = false,
                        errorMessage = throwable.message
                    )
                }
            }
            .launchIn(viewModelScope)
    }


}

data class SearchScreenUiState(
    val moviesList: ImmutableList<Movie> = persistentListOf(),
    val queryHasNoResults: Boolean = false,
    val isSearching: Boolean = false,
    val errorMessage: String? = null,
)
