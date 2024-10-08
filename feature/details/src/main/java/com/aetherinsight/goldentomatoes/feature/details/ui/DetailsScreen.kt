package com.aetherinsight.goldentomatoes.feature.details.ui

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.core.ui.common.DialDialog
import com.aetherinsight.goldentomatoes.core.ui.common.ErrorItem
import com.aetherinsight.goldentomatoes.core.ui.common.shimmerBrush
import com.aetherinsight.goldentomatoes.core.ui.theme.BackgroundColor
import com.aetherinsight.goldentomatoes.core.ui.theme.BackgroundColorAccent
import com.aetherinsight.goldentomatoes.core.ui.theme.Black_50
import com.aetherinsight.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.aetherinsight.goldentomatoes.core.ui.theme.TextColor
import com.aetherinsight.goldentomatoes.feature.details.R
import com.aetherinsight.goldentomatoes.feature.details.utils.calculateMinutesDifference
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailsRoot(
    movieId: Long,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    var askForSchedule by remember { mutableStateOf(false) }

    var scheduleMinutes = rememberSaveable { mutableLongStateOf(0L) }

    val notificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.loadMovieDetails(movieId)
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
                        contentDescription = stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.back_button_description)
                    )
                }

                val notificationButtonIcon: ImageVector =
                    if (uiState.movieDetails?.scheduled == true) Icons.Filled.Notifications else Icons.Outlined.Notifications

                IconButton(
                    onClick = {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || notificationPermission.status.isGranted) {
                            askForSchedule = true
                        } else {
                            notificationPermission.launchPermissionRequest()
                        }
                    },
                    modifier = Modifier.statusBarsPadding(),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = TextColor,
                        containerColor = Black_50,
                    )
                ) {
                    Icon(
                        painter = rememberVectorPainter(notificationButtonIcon),
                        contentDescription = stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.notification_button_description)
                    )
                }
            }
        }
    ) { contentPadding ->
        if (uiState.isLoading) {
            Box {
                ShimmerImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                )
            }
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

        if (uiState.movieDetails != null) {
            Box {
                if (askForSchedule) {
                    DialDialog(
                        onConfirm = { timePickerState ->
                            val minutes = calculateMinutesDifference(timePickerState)
                            viewModel.onNotificationButtonClick(minutes)
                            askForSchedule = false
                        },
                        onDismiss = {
                            askForSchedule = false
                        }
                    )
                }
                DetailsScreen(
                    movieDetails = uiState.movieDetails!!,
                    onNextMovieClick = {
                        viewModel.onNextRandomMovieClick()
                    },
                    onFavoriteButtonClick = { viewModel.onFavoriteButtonClick() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                )
            }
        }
    }
}

@Composable
fun ShimmerImage(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = "",
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(shimmerBrush(true))
        )

        Column(
            modifier = modifier
                .padding(horizontal = 12.dp)
        ) {
            Text(
                "                 ",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.background(shimmerBrush(true))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "                                                                                                                        ",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.background(shimmerBrush(true))
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
    movieDetails: MovieGlobal,
    onNextMovieClick: () -> Unit,
    onFavoriteButtonClick: () -> Unit,
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
            favorite = movieDetails.favorite,
            onNotificationButtonClick = onFavoriteButtonClick,
            onNextMovieClick = onNextMovieClick,
        )
    }
}

@Composable
private fun BottomButtons(
    favorite: Boolean,
    onNotificationButtonClick: () -> Unit,
    onNextMovieClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        var favoriteButtonIcon: ImageVector = Icons.Outlined.FavoriteBorder

        if (favorite) favoriteButtonIcon = Icons.Filled.Favorite


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
                            contentDescription = stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }

                    val notificationButtonIcon: ImageVector =
                        if (true) Icons.Filled.Notifications else Icons.Outlined.Notifications

                    IconButton(
                        onClick = { },
                        modifier = Modifier.statusBarsPadding(),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = TextColor,
                            containerColor = Black_50,
                        )
                    ) {
                        Icon(
                            painter = rememberVectorPainter(notificationButtonIcon),
                            contentDescription = stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }
                }
            }
        ) { contentPadding ->
            DetailsScreen(
                movieDetails = MovieGlobal(
                    1,
                    "Title",
                    "Description",
                    "",
                    scheduled = true,
                    favorite = false
                ),
                onNextMovieClick = { },
                onFavoriteButtonClick = { },
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
                            contentDescription = stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.back_button_description)
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
                            contentDescription = stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.back_button_description)
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
                            contentDescription = stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.back_button_description)
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
                            contentDescription = stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }
                }
            }
        ) { contentPadding ->
            ShimmerImage(modifier = Modifier.fillMaxSize().padding(contentPadding))
        }
    }
}