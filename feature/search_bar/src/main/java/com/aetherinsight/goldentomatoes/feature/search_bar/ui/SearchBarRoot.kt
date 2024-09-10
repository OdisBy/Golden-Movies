package com.aetherinsight.goldentomatoes.feature.search_bar.ui

import androidx.compose.foundation.background
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
import com.aetherinsight.goldentomatoes.core.ui.common.shimmerBrush
import com.aetherinsight.goldentomatoes.core.ui.theme.BackgroundColorAccent
import com.aetherinsight.goldentomatoes.core.ui.theme.Primary400
import com.aetherinsight.goldentomatoes.core.ui.theme.Primary500
import com.aetherinsight.goldentomatoes.core.ui.theme.TextColor
import com.aetherinsight.goldentomatoes.feature.search_bar.R
import com.aetherinsight.goldentomatoes.feature.search_bar.ui.SearchBarViewModel.SearchBarState
import kotlinx.collections.immutable.ImmutableList


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
        onMovieClicked = { goToMovieDetails(it.id) },
        onFavoriteClicked = { viewModel.favoriteMovie(it) }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBarComponent(
    searchQuery: String,
    searchBarActive: Boolean,
    state: SearchBarState,
    onChangeQuery: (String) -> Unit,
    onChangeSearchBarActive: (Boolean) -> Unit,
    onMovieClicked: (SearchMovie) -> Unit,
    onFavoriteClicked: (SearchMovie) -> Unit,
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
            when (state) {
                SearchBarState.Idle -> {
                    return@SearchBar
                }

                is SearchBarState.Error -> {
                    ErrorItem(
                        message = state.errorMessage,
                        modifier = Modifier.Companion.windowInsetsPadding(WindowInsets.Companion.ime)
                    )
                }

                SearchBarState.Searching -> {
                    ShimmerList()
                }

                is SearchBarState.SuccessfulSearch -> {

                    if (state.searchMovieList.isEmpty()) {
                        NoMoviesFounded(
                            modifier = Modifier.Companion.windowInsetsPadding(
                                WindowInsets.Companion.ime
                            )
                        )
                        return@SearchBar
                    }

                    ListWithMovies(state.searchMovieList, onMovieClicked, onFavoriteClicked)
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
    onMovieClicked: (SearchMovie) -> Unit,
    onFavoriteClicked: (SearchMovie) -> Unit,
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
                MovieSearchListItem(
                    movie = movies[index],
                    onMovieClicked = onMovieClicked,
                    onFavoriteClicked = onFavoriteClicked
                )
            }
        )
    }
}

@Composable
fun MovieSearchListItem(
    movie: SearchMovie,
    onMovieClicked: (SearchMovie) -> Unit,
    onFavoriteClicked: (SearchMovie) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    TextButton(
        onClick = {
            onMovieClicked(movie)
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

            IconButton(
                onClick = { onFavoriteClicked(movie) }
            ) {
                Icon(
                    painter = rememberVectorPainter(
                        image = if (movie.favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
                    ),
                    contentDescription = null,
                    tint = if (movie.favorite) Primary400 else TextColor,
                )
            }
        }
    }
}

@Composable
fun ShimmerList(modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.Companion
            .fillMaxSize()
    ) {
        items(
            count = 4,
            itemContent = {
                TextButton(
                    onClick = {

                    },
                    contentPadding = PaddingValues(24.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .background(shimmerBrush(showShimmer = true))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Companion.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {}
                }
            }
        )
    }
}
