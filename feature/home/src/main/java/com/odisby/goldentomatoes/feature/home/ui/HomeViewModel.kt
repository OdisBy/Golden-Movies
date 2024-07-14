package com.odisby.goldentomatoes.feature.home.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class HomeUiState(
    val isLoadingDiscover: Boolean = false,
    val isLoadingSaved: Boolean = false,
    val discoverList: List<Unit> = emptyList(),
    val scheduledList: List<Unit> = emptyList(),
)

class HomeViewModel : ViewModel() {

    private val searchText = MutableStateFlow("")
    private val isSearching = MutableStateFlow("")


    private val _state = MutableStateFlow(HomeUiState())

    val state: StateFlow<HomeUiState>
        get() = _state
}
