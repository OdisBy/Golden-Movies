package com.odisby.goldentomatoes.feature.details.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.odisby.goldentomatoes.feature.details.R

@Composable
fun DetailsRoot(
    navigateUp: () -> Unit,
    viewModel: DetailsViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
//        modifier = Modifier
//            .background(BackgroundColor)
//            .statusBarsPadding()
//            .navigationBarsPadding(),
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text("Movie Name")
//                }
//            )
//        }
    ) { contentPadding ->
        DetailsScreen(
            uiState = DetailsUiState(),
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        )
    }
}

@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
    ) {
        Image(
            modifier = Modifier
                .height(205.dp)
                .clip(MaterialTheme.shapes.extraLarge),
            painter = painterResource(R.drawable.test_banner),
            contentDescription = null,
        )

        Text(text = uiState.movieName)
    }
}