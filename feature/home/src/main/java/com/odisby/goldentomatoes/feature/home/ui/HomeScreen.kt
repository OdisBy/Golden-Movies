package com.odisby.goldentomatoes.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme

@Composable
fun HomeRoot(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = HomeViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        HomeScreen(
            uiState = uiState,
            navController = navController,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val searchText = uiState.searchText
    val isSearching = uiState.isSearching
    val searchedMovieList = uiState.searchedMovieList

    // Top search bar
//    SearchBar(
//        query = searchText,
//        onQueryChange = { },
//        onSearch = { },
//        active = isSearching,
//        onActiveChange = { },
//        placeholder = { },
//    ) {
//        LazyColumn {
//            items(searchedMovieList) { movie ->
//                Text(
//                    text = "",
//                    modifier = Modifier.padding(
//                        start = 8.dp,
//                        top = 4.dp,
//                        end = 8.dp,
//                        bottom = 4.dp
//                    )
//                )
//            }
//        }
//    }


    // discover
    // carrossel + text + navigation
    // carrosel loading surface


    // scheduled
    // carrossel + text + navigation
    // carrosel loading surface


    // fab button: rate random movies
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFF1C376D,
    name = "PIXEL",
    device = Devices.PIXEL
)
@Composable
private fun HomeScreenPreview() {
    GoldenTomatoesTheme {
        HomeScreen(
            uiState = HomeUiState(),
            navController = rememberNavController(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

