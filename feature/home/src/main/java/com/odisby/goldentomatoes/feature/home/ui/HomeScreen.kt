package com.odisby.goldentomatoes.feature.home.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.odisby.goldentomatoes.core.ui.theme.BackgroundColor
import com.odisby.goldentomatoes.core.ui.theme.Primary200
import com.odisby.goldentomatoes.core.ui.theme.Primary900
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.home.R
import com.odisby.goldentomatoes.feature.home.ui.components.SearchBarApp
import com.odisby.goldentomatoes.feature.search.model.Movie

private val moviesDumb = listOf(
    Movie(
        id = 1,
        name = "Inception",
        rating = null
    ),
    Movie(
        id = 2,
        name = "The Prestige",
        rating = null
    ),
    Movie(
        id = 3,
        name = "Interstellar",
        rating = null,
    ),
    Movie(
        id = 4,
        name = "Interworlds",
        rating = 9
    ),
    Movie(
        id = 5,
        name = "Intertest",
        rating = null
    )
)

private val moviesDumb2 = listOf(
    Movie(
        id = 1,
        name = "Inceptiasaon",
        rating = null
    ),
    Movie(
        id = 2,
        name = "The Prestasige",
        rating = null
    ),
    Movie(
        id = 3,
        name = "Interstasllar",
        rating = null,
    ),
    Movie(
        id = 4,
        name = "Interwasorlds",
        rating = 9
    ),
    Movie(
        id = 5,
        name = "Interasatest",
        rating = null
    )
)

@Composable
fun HomeRoot(
    navigateToSearchScreen: () -> Unit,
    navigateToDetailsScreen: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = HomeViewModel()
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
                Icon(Icons.Filled.Star, "Descubra filmes aleatórios")
            }
        }
    ) { contentPadding ->
        HomeScreen(
            uiState = uiState,
            onSearchButtonClick = { viewModel.runSearch(it) },
            goToMovieDetails = navigateToDetailsScreen,
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
    modifier: Modifier = Modifier
) {

    var searchBarActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
    ) {
        SearchBarApp(
            searchQuery = searchQuery,
            onSearchButtonClick = onSearchButtonClick,
            searchBarActive = searchBarActive,
            uiState = uiState,
            onChangeQuery = { searchQuery = it },
            onChangeSearchBarActive = { searchBarActive = it },
        )
        // carrosel loading surface

        Spacer(modifier = Modifier.height(24.dp))

        DiscoverNewMovies(goToMovieDetails)


        Spacer(modifier = Modifier.height(24.dp))

        ScheduledMovies(goToMovieDetails)

    }

}

@Composable
private fun DiscoverNewMovies(
    goToMovieDetails: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .semantics { isTraversalGroup = true }
    ) {
        RowTextAndGoButton(
            text = "Descubra novos filmes",
            onButtonClick = {
                // go to discover screen
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        DiscoverCarousel(moviesDumb, goToMovieDetails)
    }
}

@Composable
private fun ScheduledMovies(
    goToMovieDetails: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .semantics { isTraversalGroup = true }
    ) {
        RowTextAndGoButton(
            text = "Filmes agendados",
            onButtonClick = {
                // go to discover screen
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        ScheduledCarousel(moviesDumb2, goToMovieDetails)
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
        Image(
            modifier = Modifier
                .height(205.dp)
                .maskClip(MaterialTheme.shapes.extraLarge)
                .clickable {
                    goToMovieDetails(movie.id)
                },
            painter = painterResource(R.drawable.test_banner),
            contentDescription = "temporary content descirption",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun RowTextAndGoButton(text: String, onButtonClick: () -> Unit) {
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

@Preview(
    showBackground = true,
    backgroundColor = 0xFF181818
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState(),
        onSearchButtonClick = {},
        goToMovieDetails = {}
    )
}
