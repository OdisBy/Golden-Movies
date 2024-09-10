package com.aetherinsight.goldentomatoes.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.feature.home.data.GetDiscoverMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.data.GetFavoriteMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
) : ViewModel() {

    private val _discoverMovies = MutableStateFlow<Resource<List<HomeMovie>>>(Resource.Loading())
    val discoverMovies: StateFlow<Resource<List<HomeMovie>>> = _discoverMovies.asStateFlow()

    val favoriteMovies: StateFlow<Resource<List<HomeMovie>>> =
        getFavoriteMoviesUseCase()
            .stateIn(
                initialValue = Resource.Loading(),
                scope = viewModelScope,
                started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000)
            )

    init {
        getDiscoverMovies()
    }

    fun getDiscoverMovies() = viewModelScope.launch {
        _discoverMovies.value = Resource.Loading()

        getDiscoverMoviesUseCase()
            .flowOn(Dispatchers.Default)
            .catch { e ->
                Timber.e("Unexpected catch error ${e.message}")
                _discoverMovies.value = Resource.Error(e.localizedMessage ?: "Error")
            }
            .collect {
                _discoverMovies.value = it
            }
    }
}
