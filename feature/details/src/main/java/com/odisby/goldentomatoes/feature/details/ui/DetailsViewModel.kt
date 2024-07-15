package com.odisby.goldentomatoes.feature.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odisby.goldentomatoes.feature.details.data.GetDetailsUseCase
import com.odisby.goldentomatoes.feature.details.data.NotificationsUseCase
import com.odisby.goldentomatoes.feature.details.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val scheduleMovieUseCase: NotificationsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsUiState())

    val state: StateFlow<DetailsUiState>
        get() = _state

    init {
        _state.value = _state.value.copy(isLoading = true)


    }

    /*
    Movie Id -1 Is being treated as random movie
    */
    fun getMovieDetails(movieId: Long) = runBlocking {
        if (movieId == -1L) {
            getRandomMovieDetails()
        } else {
            try {
                val movie = getDetailsUseCase(movieId)
                _state.value = _state.value.copy(movie = movie, isLoading = false)
            } catch (e: Exception) {
                Timber.e(e)
                _state.value =
                    _state.value.copy(
                        errorMessage = e.localizedMessage ?: "Error",
                        isLoading = false
                    )
            }
        }
    }

    private suspend fun getRandomMovieDetails() {
        try {
            val movie = getDetailsUseCase.randomMovieId()
            _state.value = _state.value.copy(movie = movie, isLoading = false)
        } catch (e: Exception) {
            Timber.e(e)
            _state.value =
                _state.value.copy(errorMessage = e.localizedMessage ?: "Error", isLoading = false)
        }
    }

    fun onNotificationButtonClick() {
        viewModelScope.launch {
            try {
                val movie = state.value.movie ?: return@launch
                scheduleMovieUseCase.invoke(movie)
            } catch (e: Exception) {
                Timber.e("Não foi possível agendar o filme ${e.localizedMessage}")
            }
        }
    }
}

data class DetailsUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
