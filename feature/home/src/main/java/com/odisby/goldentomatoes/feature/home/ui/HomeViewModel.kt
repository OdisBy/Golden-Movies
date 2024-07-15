package com.odisby.goldentomatoes.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.feature.home.data.GetDiscoverMoviesUseCase
import com.odisby.goldentomatoes.feature.home.data.GetSchedulesMoviesUseCase
import com.odisby.goldentomatoes.feature.home.data.SearchMoviesUseCase
import com.odisby.goldentomatoes.feature.home.model.Movie
import com.odisby.goldentomatoes.feature.home.model.SearchMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
    private val getScheduledMoviesUseCase: GetSchedulesMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())

    val state: StateFlow<HomeUiState>
        get() = _state

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoadingDiscover = true, isLoadingScheduled = true)
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

            try {
                val result = getScheduledMoviesUseCase()
                _state.update {
                    it.copy(
                        isLoadingScheduled = false,
                        scheduledList = result
                    )

                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoadingScheduled = false,
                        scheduledList = emptyList(),
                        searchErrorMessage = e.localizedMessage ?: "Error"
                    )
                }
            }
        }
    }

    private fun getSearchMovies(searchQuery: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isSearching = true)

                val getMovies = searchMoviesUseCase.invoke(searchQuery)

                val persistent = getMovies.toPersistentList()

                _state.update {
                    it.copy(
                        movieList = persistent,
                        queryHasNoResults = persistent.isEmpty(),
                        isSearching = false,
                        searchErrorMessage = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        movieList = persistentListOf(),
                        queryHasNoResults = false,
                        isSearching = false,
                        searchErrorMessage = e.localizedMessage ?: "Error"
                    )
                }
            }
        }
    }

    fun runSearch(query: String) {
        viewModelScope.launch {
            if (query.length > 3) {
                getSearchMovies(query)
            } else {
                _state.update {
                    it.copy(
                        movieList = persistentListOf(),
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
    val isLoadingScheduled: Boolean = false,
    val discoverList: List<Movie> = emptyList(),
    val scheduledList: List<Movie> = emptyList(),
    val movieList: ImmutableList<SearchMovie> = persistentListOf(),
    val queryHasNoResults: Boolean = false,
    val isSearching: Boolean = false,
    val searchErrorMessage: String? = null,
)
