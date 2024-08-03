package com.aetherinsight.goldentomatoes.feature.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.core.ui.constants.Constants.RANDOM_MOVIE_ID
import com.aetherinsight.goldentomatoes.feature.details.data.GetDetailsUseCase
import com.aetherinsight.goldentomatoes.feature.details.data.ScheduleUseCase
import com.aetherinsight.goldentomatoes.feature.details.data.SaveMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.details.model.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val scheduleUseCase: ScheduleUseCase,
    private val saveMoviesUseCase: SaveMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsUiState())

    val state: StateFlow<DetailsUiState>
        get() = _state

    init {
        _state.update {
            it.copy(isLoading = true, errorMessage = null)
        }
    }

    /**
     * Usually We would use the savedStateHandler, get the movieId
     * but It is not supported with Compose Type Safety Navigation
     */
    fun loadMovieDetails(movieId: Long) {
        getMovieDetails(movieId)
    }

    /*
    Movie Id -1 Is being treated as random movie
    */
    private fun getMovieDetails(movieId: Long) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true, errorMessage = null
            )
        }

        if (movieId == RANDOM_MOVIE_ID) {
            getRandomMovieDetails()
            return@launch
        }

        try {
            getDetailsUseCase(movieId)
                .flowOn(Dispatchers.Default)
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

    private suspend fun getRandomMovieDetails() = viewModelScope.launch {
        try {
            getDetailsUseCase.getRandomMovieDetails()
                .flowOn(Dispatchers.Default)
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

    private fun resourceMovieDetailsHandler(resource: Resource<MovieDetails>) {
        when (resource) {
            is Resource.Success -> {
                _state.value =
                    _state.value.copy(movieDetails = resource.data, isLoading = false)
            }

            is Resource.Error -> {
                Timber.e("Resource Error ${resource.message}")
                _state.value =
                    _state.value.copy(
                        errorMessage = resource.message ?: "Error",
                        isLoading = false
                    )
            }
        }
    }

    fun onNextRandomMovieClick() {
        getMovieDetails(RANDOM_MOVIE_ID)
    }

    fun onFavoriteButtonClick() {
        viewModelScope.launch {
            try {
                val movie = state.value.movieDetails ?: return@launch
                saveMoviesUseCase.invoke(movie)
                _state.update {
                    it.copy(movieDetails = movie.copy(favorite = !movie.favorite))
                }
            } catch (e: Exception) {
                Timber.e("Não foi possível salvar o filme ${e.localizedMessage}")
            }
        }
    }

    fun onNotificationButtonClick() {
        viewModelScope.launch {
            try {
                val movie = state.value.movieDetails ?: return@launch

                if(!movie.favorite) {
                    saveMoviesUseCase.invoke(movie)
                }

                scheduleUseCase.invoke(movie)
                _state.value =
                    _state.value.copy(movieDetails = movie.copy(scheduled = !movie.scheduled, favorite = !movie.favorite))
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
