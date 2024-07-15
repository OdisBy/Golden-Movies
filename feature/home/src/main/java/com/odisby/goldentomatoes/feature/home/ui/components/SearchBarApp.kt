package com.odisby.goldentomatoes.feature.home.ui.components

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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odisby.goldentomatoes.core.ui.theme.BackgroundColorAccent
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.core.ui.theme.Primary400
import com.odisby.goldentomatoes.core.ui.theme.Primary500
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.home.R
import com.odisby.goldentomatoes.feature.home.model.SearchMovie
import com.odisby.goldentomatoes.feature.home.ui.HomeUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBarApp(
    searchQuery: String,
    onSearchButtonClick: (String) -> Unit,
    searchBarActive: Boolean,
    uiState: HomeUiState,
    onChangeQuery: (String) -> Unit,
    onChangeSearchBarActive: (Boolean) -> Unit,
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = { onChangeQuery(it) },
        onSearch = {
            onSearchButtonClick(it)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_movie_placeholder),
                style = MaterialTheme.typography.labelLarge
            )
        },
        active = searchBarActive,
        onActiveChange = { onChangeSearchBarActive(it) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (searchQuery.isNotBlank() && searchBarActive) {
                IconButton(onClick = { onChangeQuery("") }) {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Close),
                        tint = TextColor,
                        contentDescription = stringResource(R.string.clear_query)
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                painter = rememberVectorPainter(Icons.Default.Search),
                tint = TextColor,
                contentDescription = null
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = BackgroundColorAccent,
            dividerColor = Primary500,
        )
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
        if (uiState.movieList.isNotEmpty()) {
            ListWithMovies(uiState.movieList)
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
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun ListWithMovies(movies: ImmutableList<SearchMovie>) {
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
    movie: SearchMovie,
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
            Text(text = movie.title, color = TextColor, style = MaterialTheme.typography.bodyMedium)
            if (movie.scheduled) {
                Icon(
                    painter = rememberVectorPainter(Icons.Filled.Notifications),
                    contentDescription = null,
                    tint = Primary400
                )
            } else {
                Icon(
                    painter = rememberVectorPainter(Icons.Outlined.Notifications),
                    contentDescription = null,
                    tint = TextColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchBarAppPreview() {
    GoldenTomatoesTheme {
        SearchBarApp(
            searchQuery = "",
            onSearchButtonClick = {},
            searchBarActive = false,
            uiState = HomeUiState(),
            onChangeQuery = {},
            onChangeSearchBarActive = {}
        )
    }
}