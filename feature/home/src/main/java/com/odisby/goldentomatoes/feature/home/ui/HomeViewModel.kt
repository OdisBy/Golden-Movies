package com.odisby.goldentomatoes.feature.home.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class HomeUiState(
    val isLoadingDiscover: Boolean = false,
    val isLoadingSaved: Boolean = false,
    val discoverList: List<Unit> = emptyList(),
    val scheduledList: List<Unit> = emptyList(),
    val searchText: String = "",
    val isSearching: Boolean = false,
    val searchedMovieList: List<Unit> = emptyList()
)

@HiltViewModel
class HomeViewModel() : ViewModel() {

    private val _state = MutableStateFlow<HomeUiState>(HomeUiState())

    val uiState: StateFlow<HomeUiState>
        get() = _state

}