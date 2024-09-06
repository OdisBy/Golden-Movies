package com.aetherinsight.goldentomatoes.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.feature.home.data.GetDiscoverMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.data.GetFavoriteMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.search_bar.data.SearchMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
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
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())

    val state: StateFlow<HomeUiState>
        get() = _state

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoadingDiscover = true, isLoadingFavorite = true)
            }

            getDiscoverMovies()
        }
    }

    fun getDiscoverMovies() = viewModelScope.launch {
        try {
            _state.update {
                it.copy(discoverMoviesError = null)
            }

            getDiscoverMoviesUseCase()
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    Timber.e("Unexpected catch error ${e.message}")
                    _state.update {
                        it.copy(
                            isLoadingDiscover = false,
                            discoverList = persistentListOf(),
                            discoverMoviesError = e.localizedMessage ?: "Error"
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
                    discoverMoviesError = e.localizedMessage ?: "Error"
                )
            }
        }
    }

    private fun discoverMoviesHandler(resource: Resource<List<HomeMovie>>) {
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
                        isLoadingDiscover = false
                    )
            }
        }
    }

    fun getFavoriteMovies() = viewModelScope.launch {
        try {
            getFavoriteMoviesUseCase()
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    Timber.e("Unexpected catch error ${e.message}")
                    _state.value =
                        _state.value.copy(
                            isLoadingFavorite = false,
                            favoriteList = persistentListOf(),
                        )
                }
                .collect { result ->
                    _state.value =
                        _state.value.copy(
                            favoriteList = result.toPersistentList(),
                            isLoadingFavorite = false
                        )
                }

        } catch (e: Exception) {
            _state.value =
                _state.value.copy(
                    isLoadingFavorite = false,
                    favoriteList = persistentListOf(),
                )
        }
    }

    data class HomeUiState(
        val isLoadingDiscover: Boolean = false,
        val isLoadingFavorite: Boolean = false,
        val discoverList: ImmutableList<HomeMovie> = persistentListOf(),
        val favoriteList: ImmutableList<HomeMovie> = persistentListOf(),
        val discoverMoviesError: String? = null,
    )
}
