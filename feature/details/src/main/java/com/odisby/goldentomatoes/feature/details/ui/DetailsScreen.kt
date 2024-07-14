package com.odisby.goldentomatoes.feature.details.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odisby.goldentomatoes.core.ui.theme.BackgroundColor
import com.odisby.goldentomatoes.core.ui.theme.BackgroundColorAccent
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.details.R

@Composable
fun DetailsRoot(
    navigateUp: () -> Unit,
    viewModel: DetailsViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier
            .background(BackgroundColor)
//            .statusBarsPadding()
            .navigationBarsPadding(),
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
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
            painter = painterResource(R.drawable.test_banner),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = modifier
                .padding(horizontal = 12.dp)
        ) {
            Text(
                uiState.movieName,
                color = TextColor,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                uiState.movieDescription,
                color = TextColor,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = TextColor,
                    containerColor = BackgroundColorAccent
                )
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                    contentDescription = null,
                )
            }

            Button(
                onClick = { },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = TextColor,
                    containerColor = BackgroundColorAccent
                )
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.Notifications),
                    contentDescription = null,
                )
            }

            Button(
                onClick = { },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = TextColor,
                    containerColor = BackgroundColorAccent
                )
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowRight),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    GoldenTomatoesTheme {
        Scaffold() { contentPadding ->
            DetailsScreen(
                uiState = DetailsUiState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            )
        }
    }
}