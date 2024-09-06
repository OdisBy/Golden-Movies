package com.aetherinsight.goldentomatoes.feature.search_bar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aetherinsight.goldentomatoes.core.data.model.SearchMovie
import com.aetherinsight.goldentomatoes.core.ui.common.ErrorItem
import com.aetherinsight.goldentomatoes.core.ui.theme.BackgroundColorAccent
import com.aetherinsight.goldentomatoes.core.ui.theme.Primary400
import com.aetherinsight.goldentomatoes.core.ui.theme.Primary500
import com.aetherinsight.goldentomatoes.core.ui.theme.TextColor
import com.aetherinsight.goldentomatoes.feature.search_bar.R
import com.aetherinsight.goldentomatoes.feature.search_bar.ui.SearchBarViewModel.SearchBarState
import kotlinx.collections.immutable.ImmutableList
import kotlin.text.isNotBlank


@Composable
fun SearchBarRoot(
    searchBarActive: Boolean,
    onChangeSearchBarActive: (Boolean) -> Unit,
    goToMovieDetails: (Long) -> Unit,
    viewModel: SearchBarViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()


    SearchBarComponent(
        searchQuery = searchQuery,
        searchBarActive = searchBarActive,
        state = state,
        onChangeQuery = { viewModel.onInputQueryChange(it) },
        onChangeSearchBarActive = onChangeSearchBarActive,
        onMovieClicked = goToMovieDetails,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBarComponent(
    searchQuery: String,
    searchBarActive: Boolean,
    state: SearchBarViewModel.SearchBarState,
    onChangeQuery: (String) -> Unit,
    onChangeSearchBarActive: (Boolean) -> Unit,
    onMovieClicked: (Long) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        if (searchBarActive) Modifier.Companion.fillMaxSize() else Modifier.Companion.padding(
            horizontal = 12.dp
        )
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { onChangeQuery(it) },
            onSearch = { keyboardController?.hide() },
            placeholder = {
                Text(
                    text = stringResource(R.string.search_movie_placeholder),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            active = searchBarActive,
            onActiveChange = { onChangeSearchBarActive(it) },
            modifier = Modifier.Companion.fillMaxWidth(),
            trailingIcon = {
                if (searchQuery.isNotBlank() && searchBarActive) {
                    IconButton(onClick = { onChangeQuery("") }) {
                        Icon(
                            painter = rememberVectorPainter(
                                Icons.Default.Close
                            ),
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
            when (val uiState = state) {
                SearchBarState.Idle -> {
                    return@SearchBar
                }

                is SearchBarState.Error -> {
                    ErrorItem(
                        message = uiState.errorMessage,
                        modifier = Modifier.Companion.windowInsetsPadding(WindowInsets.Companion.ime)
                    )
                }

                SearchBarState.Searching -> {
                    SearchingItem(modifier = Modifier.Companion.align(Alignment.Companion.CenterHorizontally))
                }

                is SearchBarState.SuccessfulSearch -> {

                    if (uiState.searchMovieList.isEmpty()) {
                        NoMoviesFounded(
                            modifier = Modifier.Companion.windowInsetsPadding(
                                WindowInsets.Companion.ime
                            )
                        )
                        return@SearchBar
                    }

                    ListWithMovies(uiState.searchMovieList, onMovieClicked)
                }
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
fun NoMoviesFounded(modifier: Modifier = Modifier.Companion) {
    Column(
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        ErrorItem(
            message = stringResource(
                R.string.no_movies_founded
            )
        )
    }
}

@Composable
private fun ListWithMovies(
    movies: ImmutableList<SearchMovie>,
    onMovieClicked: (Long) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.Companion.fillMaxSize()
    ) {
        items(
            count = movies.size,
            key = { index -> movies[index].id },
            itemContent = { index ->
                MovieSearchListItem(movie = movies[index], onMovieClicked = onMovieClicked)
            }
        )
    }
}

@Composable
fun MovieSearchListItem(
    movie: SearchMovie,
    onMovieClicked: (Long) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    TextButton(
        onClick = {
            onMovieClicked(movie.id)
        },
        contentPadding = PaddingValues(24.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Companion.CenterVertically,
            modifier = Modifier.Companion.fillMaxWidth()
        ) {
            Text(
                text = movie.title,
                color = TextColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.Companion
                    .weight(1f)
                    .padding(end = 16.dp)
            )

            Icon(
                painter = rememberVectorPainter(
                    image = if (movie.favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
                ),
                contentDescription = null,
                tint = if (movie.favorite) Primary400 else TextColor
            )
        }
    }
}
