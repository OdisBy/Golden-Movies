package com.odisby.goldentomatoes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.odisby.goldentomatoes.core.ui.constants.ListTypes
import com.odisby.goldentomatoes.feature.details.ui.DetailsRoot
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
                    navController.navigate(MovieListScreen(type.toRoute()))
                },
            )
        }
        composable<DetailsScreen> { backStackEntry ->
            val detailsScreen: DetailsScreen = backStackEntry.toRoute()
            DetailsRoot(
                movieId = detailsScreen.id,
                navigateUp = {
                    navController.navigateUp()
                },
            )
        }
        composable<MovieListScreen> { backStackEntry ->
            val movieListScreen: MovieListScreen = backStackEntry.toRoute()
            MovieListRoot(
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToDetailsScreen = { id ->
                    navController.navigate(DetailsScreen(id))
                },
                listType = ListTypes.fromRoute(movieListScreen.type)
            )
        }
    }
}
