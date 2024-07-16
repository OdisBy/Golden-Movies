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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
    private val getScheduledMoviesUseCase: GetSchedulesMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())

    val state: StateFlow<HomeUiState>
        get() = _state

    /**
     * Input Text flow to Search Bar
     * It is decoupled from UiState because It has to handle debounce
     */
    private val _inputText: MutableStateFlow<String> =
        MutableStateFlow("")

    val inputText: StateFlow<String> = _inputText

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoadingDiscover = true, isLoadingScheduled = true)
            }

            getDiscoverMovies()

            // Collect Latest cancel last action when a new action is emitted
            inputText.debounce(500).collectLatest { input ->
                runSearch(input)
            }
        }
    }

    private fun getDiscoverMovies() = viewModelScope.launch {
        try {
            _state.update {
                it.copy(searchErrorMessage = "")
            }

            getDiscoverMoviesUseCase()
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    Timber.e("Unexpected catch error ${e.message}")
                    _state.update {
                        it.copy(
                            isLoadingDiscover = false,
                            discoverList = persistentListOf(),
                            searchErrorMessage = e.localizedMessage ?: "Error"
                        )
                    }
                }
                .collect {
                    discoverMoviesHandler(it)
                }

        } catch (e: Exception) {
            Timber.e(e)
            _state.update {
                it.copy(
                    isLoadingDiscover = false,
                    discoverList = persistentListOf(),
                    searchErrorMessage = e.localizedMessage ?: "Error"
                )
            }
        }
    }

    private fun discoverMoviesHandler(resource: Resource<List<Movie>>) {
        when (resource) {
            is Resource.Success -> {
                _state.value =
                    _state.value.copy(
                        discoverList = resource.data.toPersistentList(),
                        isLoadingDiscover = false
                    )
            }

            is Resource.Error -> {
                _state.value =
                    _state.value.copy(
                        discoverList = persistentListOf(),
                        searchErrorMessage = resource.message ?: "Error",
                        isLoadingDiscover = false
                    )
            }
        }
    }

    fun getScheduledMovies() = viewModelScope.launch {
        try {
            getScheduledMoviesUseCase()
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    Timber.e("Unexpected catch error ${e.message}")
                    _state.value =
                        _state.value.copy(
                            isLoadingScheduled = false,
                            scheduledList = persistentListOf(),
                            searchErrorMessage = e.localizedMessage ?: "Error"
                        )
                }
                .collect { result ->
                    _state.value =
                        _state.value.copy(
                            scheduledList = result.toPersistentList(),
                            isLoadingScheduled = false
                        )
                }

        } catch (e: Exception) {
            _state.value =
                _state.value.copy(
                    isLoadingScheduled = false,
                    scheduledList = persistentListOf(),
                    searchErrorMessage = e.localizedMessage ?: "Error"
                )
        }
    }

    private fun getSearchMovies(searchQuery: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isSearching = true, searchQuery = searchQuery)

                searchMoviesUseCase.invoke(searchQuery)
                    .flowOn(Dispatchers.Default)
                    .catch { e ->
                        Timber.e("Unexpected catch error ${e.message}")
                        _state.value =
                            _state.value.copy(
                                searchMovieList = persistentListOf(),
                                queryHasNoResults = false,
                                isSearching = false,
                                searchErrorMessage = e.localizedMessage ?: "Error"
                            )
                    }
                    .collect {
                        _state.value =
                            _state.value.copy(
                                searchMovieList = it.toPersistentList(),
                                queryHasNoResults = it.isEmpty(),
                                isSearching = false,
                                searchErrorMessage = null
                            )
                    }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        searchMovieList = persistentListOf(),
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
                        searchMovieList = persistentListOf(),
                        queryHasNoResults = false,
                        isSearching = false,
                        searchErrorMessage = null
                    )
                }
            }
        }
    }

    fun updateInput(inputText: String) {
        _inputText.update { inputText }
    }
}

data class HomeUiState(
    val isLoadingDiscover: Boolean = false,
    val isLoadingScheduled: Boolean = false,
    val discoverList: ImmutableList<Movie> = persistentListOf(),
    val scheduledList: ImmutableList<Movie> = persistentListOf(),
    val searchMovieList: ImmutableList<SearchMovie> = persistentListOf(),
    val searchQuery: String = "",
    val queryHasNoResults: Boolean = false,
    val isSearching: Boolean = false,
    val searchErrorMessage: String? = null,
)
