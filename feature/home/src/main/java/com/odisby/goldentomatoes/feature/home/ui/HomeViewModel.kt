package com.odisby.goldentomatoes.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.feature.home.data.GetDiscoverMoviesUseCase
import com.odisby.goldentomatoes.feature.home.model.Movie
import com.odisby.goldentomatoes.feature.home.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())

    val state: StateFlow<HomeUiState>
        get() = _state


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
        viewModelScope.launch {
            _state.update {
                it.copy(isLoadingDiscover = true)
            }

            when (val result = getDiscoverMoviesUseCase()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoadingDiscover = false,
                            discoverList = result.data
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoadingDiscover = false,
                            discoverList = emptyList(),
                            searchErrorMessage = result.message ?: "Error"
                        )
                    }
                }
            }
        }
    }

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

    fun runSearch(query: String) {
        viewModelScope.launch {
            if (query.length > 3) {
                _state.value = _state.value.copy(isSearching = true)

                val getMovies = viewModelScope.runCatching {
                    delay(2000)
                    getMovies(query)
                }.getOrElse {
                    emptyList()
                }

                val result = getMovies.toPersistentList()

                _state.update {
                    it.copy(
                        moviesList = result,
                        queryHasNoResults = result.isEmpty(),
                        isSearching = false,
                        searchErrorMessage = null
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        moviesList = persistentListOf(),
                        queryHasNoResults = false,
                        isSearching = false,
                        searchErrorMessage = null
                    )
                }
            }
        }

    }
}

data class HomeUiState(
    val isLoadingDiscover: Boolean = false,
    val isLoadingSaved: Boolean = false,
    val discoverList: List<Movies> = emptyList(),
    val scheduledList: List<Unit> = emptyList(),
    val moviesList: ImmutableList<Movie> = persistentListOf(),
    val queryHasNoResults: Boolean = false,
    val isSearching: Boolean = false,
    val searchErrorMessage: String? = null,
)
