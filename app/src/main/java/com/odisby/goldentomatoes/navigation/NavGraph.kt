package com.odisby.goldentomatoes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.odisby.goldentomatoes.feature.details.ui.DetailsRoot
import com.odisby.goldentomatoes.feature.details.ui.DetailsViewModel
import com.odisby.goldentomatoes.feature.home.ui.HomeRoot
import com.odisby.goldentomatoes.feature.movielist.ui.MovieListRoot


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen,
    ) {
        composable<HomeScreen> {
            HomeRoot(
                navigateToDetailsScreen = { id ->
                    navController.navigate(DetailsScreen(id))
                },
                navigateToMovieList = { type ->
                    navController.navigate(MovieListScreen(type))
                }
            )
        }
        composable<DetailsScreen> {
            DetailsRoot(
                navigateUp = {
                    navController.navigateUp()
                },
                viewModel = DetailsViewModel()
            )
        }
        composable<MovieListScreen> {
            MovieListRoot(
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToDetailsScreen = { id ->
                    navController.navigate(DetailsScreen(id))
                }
            )
        }
    }
}
