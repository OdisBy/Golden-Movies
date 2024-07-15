package com.odisby.goldentomatoes.feature.details.ui

import androidx.lifecycle.ViewModel
import com.odisby.goldentomatoes.feature.details.data.GetDetailsUseCase
import com.odisby.goldentomatoes.feature.details.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsUiState())

    val state: StateFlow<DetailsUiState>
        get() = _state

    init {
        _state.value = _state.value.copy(isLoading = true)
    }


    fun getMovieDetails(movieId: Long) = runBlocking {
        try {
            val movie = getDetailsUseCase(movieId)
            _state.value = _state.value.copy(movie = movie, isLoading = false)
        } catch (e: Exception) {
            Timber.e(e)
            _state.value =
                _state.value.copy(errorMessage = e.localizedMessage ?: "Error", isLoading = false)
        }
    }
}

data class DetailsUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
