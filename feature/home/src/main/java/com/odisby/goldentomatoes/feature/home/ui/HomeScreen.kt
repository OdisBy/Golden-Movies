//package com.odisby.goldentomatoes.feature.home.ui
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.safeDrawing
//import androidx.compose.foundation.layout.windowInsetsPadding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Devices
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
//import com.odisby.goldentomatoes.core.ui.theme.TextColor
//import com.odisby.goldentomatoes.feature.home.ui.search_screen.SearchScreenHome
//
//@Composable
//fun HomeRoot(
//    navController: NavController,
//    modifier: Modifier = Modifier,
//    viewModel: HomeViewModel = HomeViewModel()
//) {
////    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
////    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize(),
//    ) { contentPadding ->
//        if (true) {
//            SearchScreenHome(
//                searchText = viewModel.searchText,
//                searchedMovieList = searchResults,
//                onQueryChange = viewModel::onSearchQueryChange,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(contentPadding)
//            )
//        } else {
//            HomeScreen(
//                uiState = HomeUiState(),
//                navController = navController,
//                onSearchButtonClick = viewModel::onSearchButtonClick,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(contentPadding)
//            )
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(
//    uiState: HomeUiState,
//    onSearchButtonClick: () -> Unit,
//    navController: NavController,
//    modifier: Modifier = Modifier
//) {
//
//    Column(
//        modifier = modifier
//            .windowInsetsPadding(WindowInsets.safeDrawing)
//            .padding(top = 16.dp)
//    ) {
//        IconButton(
//            onClick = onSearchButtonClick
//        ) {
//            Icon(
//                imageVector = Icons.Default.Search,
//                tint = TextColor,
//                contentDescription = null
//            )
//        }
//    }
//
//    // discover
//    // carrossel + text + navigation
//    // carrosel loading surface
//
//
//    // scheduled
//    // carrossel + text + navigation
//    // carrosel loading surface
//
//
//    // fab button: rate random movies
//}
//
//
//@Preview(
//    showBackground = true,
//    backgroundColor = 0xFF181818,
//    name = "PIXEL",
//    device = Devices.PIXEL,
//    showSystemUi = true
//)
//@Composable
//private fun HomeScreenPreview() {
//    GoldenTomatoesTheme {
//        HomeScreen(
//            uiState = HomeUiState(),
//            navController = rememberNavController(),
//            onSearchButtonClick = {},
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}
////
////@Preview(
////    showBackground = true,
////    backgroundColor = 0xFF181818,
////    name = "PIXEL",
////    device = Devices.PIXEL,
////    showSystemUi = true
////)
////@Composable
////private fun SearchScreenHomePreview() {
////    GoldenTomatoesTheme {
////        SearchScreenHome(
////            uiState = HomeUiState(isSearching = true),
////            onQueryChange = { },
////            modifier = Modifier.fillMaxSize()
////        )
////    }
////}
//
//
////@Preview(
////    showBackground = true,
////    backgroundColor = 0xFF181818,
////)
////@Composable
////private fun MovieSearchListItemPreview() {
////    GoldenTomatoesTheme {
////        Column(Modifier.padding(24.dp)) {
////            MovieSearchListItem(
////                movie = Movie(
////                    id = 1,
////                    name = "Inception",
////                    rating = 8.8
////                ),
////            )
////        }
////    }
////}
