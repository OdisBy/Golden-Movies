package com.odisby.goldentomatoes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.odisby.goldentomatoes.feature.home.ui.HomeRoot


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen,
    ) {
        composable<HomeScreen> {
            HomeRoot(
                navigateToSearchScreen = {
//                    navController.navigate(SearchScreen)
                }
            )
        }
//        composable<SearchScreen> {
//            val viewModel = SearchViewModel()
//            SearchRoot(
////                navigateToDetailsScreen = { id ->
////
////                }
//                viewModel = viewModel,
//                navigateToHomeScreen = {
//                    navController.navigateUp()
//                }
//            )
//        }
    }
}
