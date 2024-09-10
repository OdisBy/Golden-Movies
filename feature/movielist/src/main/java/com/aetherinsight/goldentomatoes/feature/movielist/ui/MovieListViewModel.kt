package com.aetherinsight.goldentomatoes.feature.movielist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.core.ui.constants.ListTypes
import com.aetherinsight.goldentomatoes.feature.movielist.data.GetListMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.movielist.model.MovieListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getListMoviesUseCase: GetListMoviesUseCase
) : ViewModel() {

    private val _movieListState =
        MutableStateFlow<Resource<List<MovieListItem>>>(Resource.Loading())

    val movieListState: StateFlow<Resource<List<MovieListItem>>>
        get() = _movieListState

    fun getDiscoverMovies(type: ListTypes) = viewModelScope.launch {
        getListMoviesUseCase.invoke(type)
            .flowOn(Dispatchers.Default)
            .onStart {
                _movieListState.value = Resource.Loading()
            }
            .catch { e ->
                Timber.e("Unexpected catch error ${e.message}")
                _movieListState.value = Resource.Error(e.localizedMessage ?: "Error")
            }
            .collect {
                _movieListState.value = it
            }
    }

}
