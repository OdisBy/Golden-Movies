package com.odisby.goldentomatoes.feature.home.ui

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.odisby.goldentomatoes.core.ui.theme.Primary400
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.home.R
import com.odisby.goldentomatoes.feature.search.model.Movie
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HomeRoot(
    navigateToSearchScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = HomeViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { contentPadding ->
        HomeScreen(
            uiState = uiState,
            onSearchButtonClick = { viewModel.runSearch(it) },
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSearchButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var searchBarActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {
                onSearchButtonClick(it)
            },
            placeholder = {
                Text(text = stringResource(R.string.search_movie_placeholder))
            },
            active = searchBarActive,
            onActiveChange = { searchBarActive = it },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (searchQuery.isNotBlank() && searchBarActive) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = TextColor,
                            contentDescription = stringResource(R.string.clear_query)
                        )
                    }
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = TextColor,
                    contentDescription = null
                )
            },
        ) {
            if (searchQuery.isBlank()) {
                return@SearchBar
            }
            if (uiState.isSearching) {
                SearchingItem(modifier = Modifier.align(Alignment.CenterHorizontally))
                return@SearchBar
            }
            if (uiState.searchErrorMessage != null) {
                ErrorItem(
                    message = uiState.searchErrorMessage,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.ime)
                )
                return@SearchBar
            }
            if (uiState.queryHasNoResults) {
                NoMoviesFounded(modifier = Modifier.windowInsetsPadding(WindowInsets.ime))
                return@SearchBar
            }
            if (uiState.moviesList.isNotEmpty()) {
                ListWithMovies(uiState.moviesList)
            }
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

