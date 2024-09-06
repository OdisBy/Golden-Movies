package com.aetherinsight.goldentomatoes.feature.search_bar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aetherinsight.goldentomatoes.core.data.model.SearchMovie
import com.aetherinsight.goldentomatoes.feature.search_bar.data.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchBarViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {

    companion object {
        private const val DEBOUNCE_TIME = 500L
        private const val MIN_QUERY_LENGTH = 4
    }

    private val _searchQuery: MutableStateFlow<String> =
        MutableStateFlow("")

    val searchQuery: StateFlow<String> = _searchQuery

    private val _state = MutableStateFlow<SearchBarState>(SearchBarState.Idle)

    val state: StateFlow<SearchBarState>
        get() = _state

    init {
        viewModelScope.launch {
            searchQuery.debounce(DEBOUNCE_TIME).collectLatest { input ->
                if (input.isBlank() || input.length < MIN_QUERY_LENGTH) {
                    _state.update { SearchBarState.Idle }
                } else {
                    runSearch(input)
                }
            }
        }
    }


    fun onInputQueryChange(newQuery: String) {
        _searchQuery.update { newQuery }

    }

    fun favoriteMovie(movieId: Long) {

    }

    suspend fun runSearch(query: String) {
        _state.update { SearchBarState.Searching }

        searchMoviesUseCase.invoke(query)
            .catch { e ->
                Timber.e("Unexpected catch error ${e.message}")
                _state.update { SearchBarState.Error(e.localizedMessage ?: "Erro inesperado!") }
            }
            .collectLatest { movies ->
                _state.update {
                    SearchBarState.SuccessfulSearch(searchMovieList = movies.toPersistentList())
                }
            }
    }

    sealed interface SearchBarState {
        data object Searching : SearchBarState

        data class Error(val errorMessage: String) : SearchBarState

        data class SuccessfulSearch(
            val searchMovieList: ImmutableList<SearchMovie> = persistentListOf()
        ) : SearchBarState

        data object Idle : SearchBarState
    }
}