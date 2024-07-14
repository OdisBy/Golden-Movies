package com.odisby.goldentomatoes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.odisby.goldentomatoes.feature.details.ui.DetailsRoot
import com.odisby.goldentomatoes.feature.details.ui.DetailsViewModel
import com.odisby.goldentomatoes.feature.home.ui.HomeRoot


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen,
    ) {
        composable<HomeScreen> {
            HomeRoot(
                navigateToSearchScreen = {},
                navigateToDetailsScreen = { id ->
                    navController.navigate(DetailsScreen(id))
                }
            )
        }
        composable<DetailsScreen> {
            DetailsRoot(
                navigateUp = {},
                viewModel = DetailsViewModel()
            )
        }
    }
}
