package com.odisby.goldentomatoes.feature.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.core.ui.theme.Primary400
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.search.R
import com.odisby.goldentomatoes.feature.search.model.Movie
import com.odisby.goldentomatoes.feature.search.ui.components.SearchViewApp
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SearchScreenRoot(
    navController: NavController,
    viewModel: SearchViewModel = SearchViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { contentPadding ->
        SearchScreen(
            state = state,
            query = viewModel.searchQuery,
            onQueryChange = viewModel::onQueryChange,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}

@Composable
fun SearchScreen(
    state: SearchScreenUiState,
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {
        SearchViewApp(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = { keyboardController?.hide() },
        ) {
            if (query.isBlank()) {
                return@SearchViewApp
            }
            if (state.isSearching) {
                SearchingItem(modifier = Modifier.align(Alignment.CenterHorizontally))
                return@SearchViewApp
            }
            if (state.errorMessage != null) {
                ErrorItem(
                    message = state.errorMessage,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.ime)
                )
                return@SearchViewApp
            }
            if (state.queryHasNoResults) {
                NoMoviesFounded(modifier = Modifier.windowInsetsPadding(WindowInsets.ime))
                return@SearchViewApp
            }
            if (state.moviesList.isNotEmpty()) {
                ListWithMovies(state.moviesList)
            }
        }
    }

}

@Composable
fun SearchingItem(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun NoMoviesFounded(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        UnexpectedBehaviorSad(text = stringResource(R.string.no_movies_founded))
    }
}

@Composable
fun ErrorItem(message: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        UnexpectedBehaviorSad(text = message)
    }
}

@Composable
private fun UnexpectedBehaviorSad(
    text: String,
) {
    Icon(
        painter = painterResource(id = com.odisby.goldentomatoes.core.ui.R.drawable.ic_sad_face),
        contentDescription = null,
        tint = TextColor,
        modifier = Modifier
            .height(120.dp)
            .width(120.dp)
    )
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = text,
        color = TextColor,
    )
}

@Composable
private fun ListWithMovies(movies: ImmutableList<Movie>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = movies.size,
            key = { index -> movies[index].id },
            itemContent = { index ->
                MovieSearchListItem(movie = movies[index])
            }
        )
    }
}

@Composable
fun MovieSearchListItem(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    TextButton(
        // Todo intent to Details with movie id
        onClick = { },
        contentPadding = PaddingValues(24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth(),
        ) {
            Text(text = movie.name, color = TextColor)
            if (movie.rating != null) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Primary400
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = TextColor
                )
            }
        }
    }

}


@Preview(
    showBackground = true,
    backgroundColor = 0xFF181818,
    name = "PIXEL",
    device = Devices.NEXUS_5,
)
@Composable
fun SearchScreenPreview() {
    GoldenTomatoesTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { contentPadding ->
            SearchScreen(
                state = SearchScreenUiState(),
                query = "",
                onQueryChange = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            )
        }
    }
}
