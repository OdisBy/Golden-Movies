package com.odisby.goldentomatoes.feature.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.feature.details.data.GetDetailsUseCase
import com.odisby.goldentomatoes.feature.details.data.NotificationsUseCase
import com.odisby.goldentomatoes.feature.details.model.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val scheduleMovieUseCase: NotificationsUseCase,
) : ViewModel() {

    companion object {
        const val RANDOM_MOVIE_ID = -1L
    }

    private val _state = MutableStateFlow(DetailsUiState())

    val state: StateFlow<DetailsUiState>
        get() = _state

    init {
        _state.value = _state.value.copy(isLoading = true)
    }

    /*
    Movie Id -1 Is being treated as random movie
    */
    fun getMovieDetails(movieId: Long) = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)

        if (movieId == RANDOM_MOVIE_ID) {
            getRandomMovieDetails()
            return@launch
        }

        try {
            getDetailsUseCase(movieId)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e("Unexpected catch error ${e.message}")
                    _state.value =
                        _state.value.copy(
                            errorMessage = e.localizedMessage ?: "Error",
                            isLoading = false
                        )
                }
                .collect {
                    resourceMovieDetailsHandler(it)
                }
        } catch (e: Exception) {
            Timber.e(e)
            _state.value =
                _state.value.copy(
                    errorMessage = e.localizedMessage ?: "Error",
                    isLoading = false
                )
        }
    }

    private suspend fun getRandomMovieDetails() {
        try {
            getDetailsUseCase.getRandomMovieDetails()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e("Unexpected catch error ${e.message}")
                    _state.value =
                        _state.value.copy(
                            errorMessage = e.localizedMessage ?: "Error",
                            isLoading = false
                        )
                }
                .collect {
                    resourceMovieDetailsHandler(it)
                }

        } catch (e: Exception) {
            Timber.e(e)
            _state.value =
                _state.value.copy(errorMessage = e.localizedMessage ?: "Error", isLoading = false)
        }
    }

    private fun resourceMovieDetailsHandler(it: Resource<MovieDetails>) {
        when (it) {
            is Resource.Success -> {
                _state.value =
                    _state.value.copy(movieDetails = it.data, isLoading = false)
            }

            is Resource.Error -> {
                Timber.e("Resource Error ${it.message}")
                _state.value =
                    _state.value.copy(
                        errorMessage = it.message ?: "Error",
                        isLoading = false
                    )
            }
        }
    }

    fun onNotificationButtonClick() {
        viewModelScope.launch {
            try {
                val movie = state.value.movieDetails ?: return@launch
                scheduleMovieUseCase.invoke(movie)
                _state.value =
                    _state.value.copy(movieDetails = movie.copy(scheduled = !movie.scheduled))
            } catch (e: Exception) {
                Timber.e("Não foi possível agendar o filme ${e.localizedMessage}")
            }
        }
    }
}

data class DetailsUiState(
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
