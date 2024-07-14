package com.odisby.goldentomatoes.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.core.ui.theme.TextColor

@Composable
fun HomeRoot(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = HomeViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { contentPadding ->
        HomeScreen(
            uiState = uiState,
            //TODO navigate to search screen
            onSearchButtonClick = { },
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSearchButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(top = 16.dp)
    ) {
        IconButton(
            onClick = onSearchButtonClick
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = TextColor,
                contentDescription = null
            )
        }
    }

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
    backgroundColor = 0xFF181818,
    name = "PIXEL",
    device = Devices.PIXEL,
)
@Composable
private fun HomeScreenPreview() {
    GoldenTomatoesTheme {
        HomeScreen(
            uiState = HomeUiState(),
            onSearchButtonClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}


