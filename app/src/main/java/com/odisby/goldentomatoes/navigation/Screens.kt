package com.odisby.goldentomatoes.navigation

import kotlinx.serialization.Serializable


@Serializable
object HomeScreen

@Serializable
data class DetailsScreen(val id: Long)
