package com.aetherinsight.goldentomatoes.core.ui.constants

sealed class ListTypes {
    data object DISCOVER : ListTypes()
    data object FAVORITE : ListTypes()

    fun toRoute(): String {
        return when (this) {
            DISCOVER -> "discover"
            FAVORITE -> "favorite"
        }
    }

    companion object {
        fun fromRoute(route: String): ListTypes {
            return when (route) {
                "discover" -> DISCOVER
                "favorite" -> FAVORITE
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
        }
    }
}

object Constants {
    const val RANDOM_MOVIE_ID = -1L
}
