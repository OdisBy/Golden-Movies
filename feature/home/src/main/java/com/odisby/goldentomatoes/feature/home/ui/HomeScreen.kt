package com.odisby.goldentomatoes.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.odisby.goldentomatoes.core.ui.constants.ListTypes
import com.odisby.goldentomatoes.core.ui.theme.BackgroundColor
import com.odisby.goldentomatoes.core.ui.theme.Primary200
import com.odisby.goldentomatoes.core.ui.theme.Primary900
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.home.R
import com.odisby.goldentomatoes.feature.home.model.Movie
import com.odisby.goldentomatoes.feature.home.ui.components.NoMoviesFounded
import com.odisby.goldentomatoes.feature.home.ui.components.SearchBarApp

@Composable
fun HomeRoot(
    navigateToMovieList: (ListTypes) -> Unit,
    navigateToDetailsScreen: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .background(BackgroundColor)
            .statusBarsPadding()
            .navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                goToRandomMovie()
                },
                containerColor = Primary200,
                contentColor = Primary900,
                shape = FloatingActionButtonDefaults.largeShape,
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.Filled.Star),
                    contentDescription = stringResource(R.string.home_fab_label)
                )
            }
        }
    ) { contentPadding ->
        HomeScreen(
            uiState = uiState,
            onSearchButtonClick = { viewModel.runSearch(it) },
            goToMovieDetails = navigateToDetailsScreen,
            navigateToMovieList = navigateToMovieList,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSearchButtonClick: (String) -> Unit,
    goToMovieDetails: (Long) -> Unit,
    navigateToMovieList: (ListTypes) -> Unit,
    modifier: Modifier = Modifier
) {

    var searchBarActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column {
        // Search bar should have max size, so I'll have to break this in two Columns zzzz
        SearchBarApp(
            searchQuery = searchQuery,
            onSearchButtonClick = onSearchButtonClick,
            searchBarActive = searchBarActive,
            uiState = uiState,
            onChangeQuery = { searchQuery = it },
            onChangeSearchBarActive = { searchBarActive = it },
        )
        // carrosel loading surface

        Column(
            modifier = modifier
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoadingDiscover) {
                MoviesListLoading(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                DiscoverNewMovies(
                    goToMovieDetails,
                    navigateToMovieList,
                    movies = uiState.discoverList
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoadingScheduled) {
                MoviesListLoading(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                ScheduledMovies(
                    goToMovieDetails,
                    navigateToMovieList,
                    movies = uiState.scheduledList
                )
            }

        }
    }

}

@Composable
fun MoviesListLoading(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier)
}

@Composable
private fun DiscoverNewMovies(
    goToMovieDetails: (Long) -> Unit,
    navigateToMovieList: (ListTypes) -> Unit,
    movies: List<Movie>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .semantics { isTraversalGroup = true }
    ) {

        RowTextAndGoButton(
            text = stringResource(R.string.discover_movies_title),
            onButtonClick = {
                navigateToMovieList(ListTypes.DISCOVER)
            },
            buttonVisible = movies.size > 3
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (movies.isEmpty()) {
            NoMoviesFounded(modifier = Modifier.height(200.dp))
        } else {
            DiscoverCarousel(movies, goToMovieDetails)

        }
    }
}

@Composable
private fun ScheduledMovies(
    goToMovieDetails: (Long) -> Unit,
    navigateToMovieList: (ListTypes) -> Unit,
    movies: List<Movie>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .semantics { isTraversalGroup = true }
    ) {
        RowTextAndGoButton(
            text = stringResource(R.string.schedules_movies_title),
            onButtonClick = {
                navigateToMovieList(ListTypes.SCHEDULED)
            },
            buttonVisible = movies.size > 3
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (movies.isEmpty()) {
            NoMoviesScheduled()
        } else {
            ScheduledCarousel(movies, goToMovieDetails)
        }
    }
}

@Composable
fun ScheduledCarousel(
    movies: List<Movie>,
    goToMovieDetails: (Long) -> Unit
) {
    MoviesCarousel(
        movies = movies,
        goToMovieDetails = goToMovieDetails
    )
}

@Composable
fun DiscoverCarousel(
    movies: List<Movie>,
    goToMovieDetails: (Long) -> Unit
) {
    MoviesCarousel(
        movies = movies,
        goToMovieDetails = goToMovieDetails
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MoviesCarousel(movies: List<Movie>, goToMovieDetails: (Long) -> Unit) {
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { movies.count() },
        modifier = Modifier
            .width(412.dp)
            .height(221.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
    ) { index ->
        val movie = movies[index]
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(205.dp)
                .maskClip(MaterialTheme.shapes.extraLarge)
                .clickable {
                    goToMovieDetails(movie.id)
                },
        )
    }
}

@Composable
fun NoMoviesScheduled(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.no_movies_scheduled),
        color = TextColor,
    )
}

@Composable
private fun RowTextAndGoButton(
    text: String,
    onButtonClick: () -> Unit,
    buttonVisible: Boolean = true
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = TextColor
        )

        if (buttonVisible) {
            IconButton(
                onClick = onButtonClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = TextColor
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF181818
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState(),
        onSearchButtonClick = {},
        navigateToMovieList = { },
        goToMovieDetails = {}
    )
}
