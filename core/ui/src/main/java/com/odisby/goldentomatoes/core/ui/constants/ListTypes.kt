package com.odisby.goldentomatoes.core.ui.constants

sealed class ListTypes {
    data object DISCOVER : ListTypes()
    data object SCHEDULED : ListTypes()

    fun toRoute(): String {
        return when (this) {
            DISCOVER -> "discover"
            SCHEDULED -> "scheduled"
        }
    }

    companion object {
        fun fromRoute(route: String): ListTypes {
            return when (route) {
                "discover" -> DISCOVER
                "scheduled" -> SCHEDULED
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
        }
    }
}
