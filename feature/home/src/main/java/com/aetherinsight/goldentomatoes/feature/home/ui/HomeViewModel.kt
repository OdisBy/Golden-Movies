package com.aetherinsight.goldentomatoes.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.feature.home.data.GetDiscoverMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.data.GetFavoriteMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
) : ViewModel() {

    val discoverMovies: StateFlow<Resource<List<HomeMovie>>> =
        getDiscoverMoviesUseCase()
            .distinctUntilChanged()
            .catch { e ->
                Timber.e("Unexpected catch error ${e.message}")
                emit(Resource.Error(e.message))
            }
            .stateIn(
                initialValue = Resource.Loading(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val favoriteMovies: StateFlow<Resource<List<HomeMovie>>> =
        getFavoriteMoviesUseCase()
            .distinctUntilChanged()
            .catch { e ->
                Timber.e("Unexpected catch error ${e.message}")
                emit(Resource.Error(e.message))
            }
            .stateIn(
                initialValue = Resource.Loading(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )
}
