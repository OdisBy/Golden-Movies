package com.odisby.goldentomatoes.core.ui.constants

sealed class ListTypes {
    data object DISCOVER : ListTypes()
    data object SAVED : ListTypes()

    fun toRoute(): String {
        return when (this) {
            DISCOVER -> "discover"
            SAVED -> "saved"
        }
    }

    companion object {
        fun fromRoute(route: String): ListTypes {
            return when (route) {
                "discover" -> DISCOVER
                "saved" -> SAVED
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
        }
    }
}

object Constants {
    const val RANDOM_MOVIE_ID = -1L
}
