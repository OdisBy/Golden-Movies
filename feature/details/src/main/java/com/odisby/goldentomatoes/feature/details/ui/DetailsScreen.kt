package com.odisby.goldentomatoes.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.odisby.goldentomatoes.core.ui.common.ErrorItem
import com.odisby.goldentomatoes.core.ui.theme.BackgroundColor
import com.odisby.goldentomatoes.core.ui.theme.BackgroundColorAccent
import com.odisby.goldentomatoes.core.ui.theme.Black_50
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.details.R
import com.odisby.goldentomatoes.feature.details.model.MovieDetails

@Composable
fun DetailsRoot(
    movieId: Long,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(key1 = Unit) {
        viewModel.loadMovieDetails(movieId)
    }

    fun onNotificationButtonClick() {
        viewModel.onNotificationButtonClick()
    }

    Scaffold(
        modifier = Modifier
            .background(BackgroundColor)
            .navigationBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        navigateUp()
                    },
                    modifier = Modifier.statusBarsPadding(),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = TextColor,
                        containerColor = Black_50,
                    )
                ) {
                    Icon(
                        painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                        contentDescription = stringResource(com.odisby.goldentomatoes.core.ui.R.string.back_button_description)
                    )
                }

                IconButton(
                    onClick = {
                        viewModel.onNotificationButtonClick()
                    },
                    modifier = Modifier.statusBarsPadding(),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = TextColor,
                        containerColor = Black_50,
                    )
                ) {
                    Icon(
                        painter = rememberVectorPainter(Icons.Outlined.Notifications),
                        contentDescription = stringResource(com.odisby.goldentomatoes.core.ui.R.string.back_button_description)
                    )
                }
            }
        }
    ) { contentPadding ->
        if (uiState.isLoading) {
            LoadingScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .windowInsetsPadding(WindowInsets.ime)
            )
            return@Scaffold
        }
        if (uiState.errorMessage != null) {
            ErrorScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .windowInsetsPadding(WindowInsets.ime)
            )
            return@Scaffold
        }

        // Set it instead just call DetailsScreen because recomposition isn't being triggered
        if (uiState.movieDetails != null) {
            DetailsScreen(
                movieDetails = uiState.movieDetails!!,
                onNextMovieClick = {
                    viewModel.onNextRandomMovieClick()
                },
                onNotificationButtonClick = { onNotificationButtonClick() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    CircularProgressIndicator(modifier)
}

@Composable
fun ErrorScreen(modifier: Modifier) {
    ErrorItem(modifier = modifier, message = stringResource(R.string.cant_load_movie))
}

@Composable
fun DetailsScreen(
    movieDetails: MovieDetails,
    onNextMovieClick: () -> Unit,
    onNotificationButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movieDetails.posterPath}",
            contentDescription = movieDetails.title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        )

        Column(
            modifier = modifier
                .padding(horizontal = 12.dp)
        ) {
            Text(
                movieDetails.title,
                color = TextColor,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                movieDetails.description,
                color = TextColor,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        BottomButtons(
            scheduled = movieDetails.scheduled,
            onNotificationButtonClick = onNotificationButtonClick,
            onNextMovieClick = onNextMovieClick,
        )
    }
}

@Composable
private fun BottomButtons(
    scheduled: Boolean,
    onNotificationButtonClick: () -> Unit,
    onNextMovieClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        var favoriteButtonIcon: ImageVector = Icons.Outlined.Favorite

        if (scheduled) favoriteButtonIcon = Icons.Filled.Favorite


        Button(
            onClick = {
                onNotificationButtonClick()
            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = TextColor,
                containerColor = BackgroundColorAccent
            )
        ) {
            Icon(
                painter = rememberVectorPainter(favoriteButtonIcon),
                contentDescription = null,
            )
        }

        Button(
            onClick = {
                onNextMovieClick()
            },
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

@Preview
@Composable
private fun DetailsScreenPreview() {
    GoldenTomatoesTheme {
        Scaffold(
            modifier = Modifier
                .background(BackgroundColor)
                .navigationBarsPadding(),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.statusBarsPadding(),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = TextColor,
                            containerColor = Black_50,
                        )
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                            contentDescription = stringResource(com.odisby.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }

                    IconButton(
                        onClick = { },
                        modifier = Modifier.statusBarsPadding(),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = TextColor,
                            containerColor = Black_50,
                        )
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.Outlined.Notifications),
                            contentDescription = stringResource(com.odisby.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }
                }
            }
        ) { contentPadding ->
            DetailsScreen(
                movieDetails = MovieDetails(
                    1,
                    "Title",
                    "Description",
                    "",
                    scheduled = false,
                    favorite = false
                ),
                onNextMovieClick = { },
                onNotificationButtonClick = { },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            )
        }
    }
}

@Preview
@Composable
private fun DetailsErrorPreview() {
    GoldenTomatoesTheme {
        Scaffold(
            modifier = Modifier
                .background(BackgroundColor)
                .navigationBarsPadding(),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.statusBarsPadding(),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = TextColor,
                            containerColor = Black_50,
                        )
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                            contentDescription = stringResource(com.odisby.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }

                    IconButton(
                        onClick = { },
                        modifier = Modifier.statusBarsPadding(),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = TextColor,
                            containerColor = Black_50,
                        )
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.Outlined.Notifications),
                            contentDescription = stringResource(com.odisby.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }
                }
            }
        ) { contentPadding ->
            ErrorScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF181818
)
@Composable
private fun DetailsLoadingPreview() {
    GoldenTomatoesTheme {
        Scaffold(
            modifier = Modifier
                .background(BackgroundColor)
                .navigationBarsPadding()
                .fillMaxSize(),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.statusBarsPadding(),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = TextColor,
                            containerColor = Black_50,
                        )
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                            contentDescription = stringResource(com.odisby.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }

                    IconButton(
                        onClick = { },
                        modifier = Modifier.statusBarsPadding(),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = TextColor,
                            containerColor = Black_50,
                        )
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.Outlined.Notifications),
                            contentDescription = stringResource(com.odisby.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }
                }
            }
        ) { contentPadding ->
            LoadingScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .windowInsetsPadding(WindowInsets.ime)
            )
        }
    }
}