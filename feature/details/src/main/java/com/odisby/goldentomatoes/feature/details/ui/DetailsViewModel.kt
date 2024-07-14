package com.odisby.goldentomatoes.feature.details.ui

import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {

}

data class DetailsUiState(
    val movieName: String = "",
    val movieLink: String? = null,
)
