package com.odisby.goldentomatoes.feature.movielist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.core.ui.constants.ListTypes
import com.odisby.goldentomatoes.feature.movielist.data.GetDiscoverMoviesUseCase
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListUiState())

    val state: StateFlow<MovieListUiState>
        get() = _state

    init {
        _state.update {
            it.copy(isLoading = true, errorMessage = null)
        }
    }

    fun getDiscoverMovies(type: ListTypes) = runBlocking {
        try {
            getDiscoverMoviesUseCase.invoke(type)
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    Timber.e("Unexpected catch error ${e.message}")
                    _state.value =
                        _state.value.copy(errorMessage = e.localizedMessage, isLoading = false)
                }
                .collect {
                    handleDiscoverMovies(it)
                }

        } catch (e: Exception) {
            _state.update {
                it.copy(
                    errorMessage = e.localizedMessage ?: "Error",
                    isLoading = false
                )
            }
        }
    }

    private fun handleDiscoverMovies(resource: Resource<List<MovieListItem>>) {
        when (resource) {
            is Resource.Success -> {
                _state.update {
                    it.copy(
                        moviesList = resource.data.toPersistentList(),
                        errorMessage = null,
                        isLoading = false
                    )
                }
            }

            is Resource.Error -> {
                _state.update {
                    it.copy(
                        errorMessage = resource.message,
                        isLoading = false
                    )
                }
            }
        }
    }

}

data class MovieListUiState(
    val moviesList: ImmutableList<MovieListItem> = persistentListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)