package com.odisby.goldentomatoes.navigation

import kotlinx.serialization.Serializable


@Serializable
object HomeScreen

/*
    Compose Type Safety Navigation doest not accept null values, so I will pass -1 as null
 */
@Serializable
data class DetailsScreen(val id: Long = -1)

@Serializable
data class MovieListScreen(val type: String)
