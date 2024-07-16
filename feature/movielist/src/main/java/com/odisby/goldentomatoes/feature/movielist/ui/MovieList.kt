package com.odisby.goldentomatoes.feature.movielist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.odisby.goldentomatoes.core.ui.common.ErrorItem
import com.odisby.goldentomatoes.core.ui.constants.ListTypes
import com.odisby.goldentomatoes.core.ui.theme.BackgroundColor
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.movielist.R
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListRoot(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    navigateToDetailsScreen: (Long) -> Unit,
    listType: ListTypes = ListTypes.DISCOVER,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getDiscoverMovies(listType)
    }

    val screenTitle = when (listType) {
        ListTypes.DISCOVER -> {
            "Descobrir Filmes"
        }

        ListTypes.SAVED -> {
            "Filmes Agendados"
        }

        else -> "Descobrir Filmes"
    }

    Scaffold(
        modifier = modifier
            .background(BackgroundColor)
            .statusBarsPadding()
            .navigationBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = screenTitle,
                        color = TextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateUp,
                        modifier = Modifier.statusBarsPadding(),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = TextColor,
                        )
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                            contentDescription = stringResource(com.odisby.goldentomatoes.core.ui.R.string.back_button_description)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
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

        MovieListScreen(
            movies = uiState.moviesList,
            navigateToDetailsScreen = navigateToDetailsScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    CircularProgressIndicator(modifier)
}

@Composable
fun ErrorScreen(modifier: Modifier) {
    ErrorItem(modifier = modifier, message = stringResource(R.string.cant_load_movies_list))
}

@Composable
private fun MovieListScreen(
    movies: ImmutableList<MovieListItem>,
    navigateToDetailsScreen: (Long) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(110.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
        ) {
            items(
                count = movies.size,
                key = { index -> movies[index].id },
            ) {
                val movie = movies[it]
                Column {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable(
                                onClick = {
                                    navigateToDetailsScreen(movie.id)
                                }
                            )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = movie.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun MovieListScreenPreview() {
    GoldenTomatoesTheme {
        Scaffold(
            modifier = Modifier
                .background(BackgroundColor)
                .statusBarsPadding()
                .navigationBarsPadding(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Descobrir Filmes",
                            color = TextColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { },
                            modifier = Modifier.statusBarsPadding(),
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = TextColor,
                            )
                        ) {
                            Icon(
                                painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                                contentDescription = "Voltar"
                            )
                        }
                    }
                )
            }
        ) { contentPadding ->
            MovieListScreen(
                movies = emptyList<MovieListItem>().toImmutableList(),
                navigateToDetailsScreen = { },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            )
        }
    }
}