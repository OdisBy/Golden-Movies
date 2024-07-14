package com.odisby.goldentomatoes.feature.movielist.ui

import androidx.lifecycle.ViewModel
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class MovieListViewModel : ViewModel() {
}

data class MovieListUiState(
    val moviesList: ImmutableList<MovieListItem> = persistentListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)