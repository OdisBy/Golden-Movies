package com.aetherinsight.goldentomatoes.feature.home.ui

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
import androidx.compose.material3.carousel.CarouselState
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
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
import com.aetherinsight.goldentomatoes.core.ui.RepeatOnLifecycleEffect
import com.aetherinsight.goldentomatoes.core.ui.common.TMDBAttribution
import com.aetherinsight.goldentomatoes.core.ui.constants.Constants.RANDOM_MOVIE_ID
import com.aetherinsight.goldentomatoes.core.ui.constants.ListTypes
import com.aetherinsight.goldentomatoes.core.ui.theme.BackgroundColor
import com.aetherinsight.goldentomatoes.core.ui.theme.Primary200
import com.aetherinsight.goldentomatoes.core.ui.theme.Primary900
import com.aetherinsight.goldentomatoes.core.ui.theme.TextColor
import com.aetherinsight.goldentomatoes.feature.home.R
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import com.aetherinsight.goldentomatoes.feature.home.ui.components.NoMoviesFounded
import com.aetherinsight.goldentomatoes.feature.home.ui.components.SearchBarApp

@Composable
fun HomeRoot(
    navigateToMovieList: (ListTypes) -> Unit,
    navigateToDetailsScreen: (Long) -> Unit,
    modifier: Modifier = Modifier,
    hasInternetConnection: Boolean,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val inputQuery by viewModel.inputText.collectAsStateWithLifecycle()

    RepeatOnLifecycleEffect { viewModel.getFavoriteMovies() }

    Scaffold(
        modifier = Modifier
            .background(BackgroundColor)
            .statusBarsPadding()
            .navigationBarsPadding(),
        floatingActionButton = {
            if (hasInternetConnection) {
                FloatingActionButton(
                    onClick = {
                        navigateToDetailsScreen(RANDOM_MOVIE_ID)
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
        }
    ) { contentPadding ->
        HomeScreen(
            uiState = uiState,
            discoverMoviesList = uiState.discoverList,
            favoriteMoviesList = uiState.favoriteList,
            inputQuery = inputQuery,
            onInputQueryChange = { viewModel.updateInput(it) },
            goToMovieDetails = navigateToDetailsScreen,
            navigateToMovieList = navigateToMovieList,
            hasInternetConnection = hasInternetConnection,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    discoverMoviesList: List<HomeMovie>,
    favoriteMoviesList: List<HomeMovie>,
    inputQuery: String,
    onInputQueryChange: (String) -> Unit,
    goToMovieDetails: (Long) -> Unit,
    navigateToMovieList: (ListTypes) -> Unit,
    hasInternetConnection: Boolean,
    modifier: Modifier = Modifier
) {

    var searchBarActive by remember { mutableStateOf(false) }

    Column {
        // Search bar should have max size, so I'll have to break this in two Columns zzzz
        SearchBarApp(
            searchQuery = inputQuery,
            searchBarActive = searchBarActive,
            uiState = uiState,
            onChangeQuery = onInputQueryChange,
            onChangeSearchBarActive = { searchBarActive = it },
            onMovieClicked = goToMovieDetails
        )
        // carrosel loading surface

        Column(
            modifier = modifier
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            if (hasInternetConnection) {
                if (uiState.isLoadingDiscover) {
                    MoviesListLoading(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    DiscoverNewMovies(
                        goToMovieDetails,
                        navigateToMovieList,
                        discoverMoviesList
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

            }
            if (uiState.isLoadingFavorite) {
                MoviesListLoading(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                FavoriteMovies(
                    goToMovieDetails,
                    navigateToMovieList,
                    favoriteMoviesList
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
    homeMovies: List<HomeMovie>,
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
            buttonVisible = homeMovies.size > 3
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (homeMovies.isEmpty()) {
            NoMoviesFounded(modifier = Modifier.height(200.dp))
        } else {
            DiscoverCarousel(homeMovies, goToMovieDetails)

        }
    }
}

@Composable
private fun FavoriteMovies(
    goToMovieDetails: (Long) -> Unit,
    navigateToMovieList: (ListTypes) -> Unit,
    favoriteMovies: List<HomeMovie>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .semantics { isTraversalGroup = true }
    ) {
        RowTextAndGoButton(
            text = stringResource(R.string.favorite_movies_title),
            onButtonClick = {
                navigateToMovieList(ListTypes.FAVORITE)
            },
            buttonVisible = favoriteMovies.size > 3
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (favoriteMovies.isEmpty()) {
            NoMoviesFavorite()
        } else {
            FavoriteCarousel(favoriteMovies, goToMovieDetails)
        }
        TMDBAttribution()
    }
}

@Composable
fun FavoriteCarousel(
    homeMovies: List<HomeMovie>,
    goToMovieDetails: (Long) -> Unit
) {
    MoviesCarousel(
        homeMovies = homeMovies,
        goToMovieDetails = goToMovieDetails
    )
}

@Composable
fun DiscoverCarousel(
    homeMovies: List<HomeMovie>,
    goToMovieDetails: (Long) -> Unit
) {
    MoviesCarousel(
        homeMovies = homeMovies,
        goToMovieDetails = goToMovieDetails
    )
}

/*
    Aqui tem um bug desse carousel no Compose, quando  se usa o rememberCarouselState

    O bug é: quando a lista atualiza, e ele ainda não tem filmes...
    o suficiente para preencher a tela o app crasha ou não atualiza.

    Nesse caso então por exemplo se adicionarmos um filme, e o carousel não estiver preenchido e voltar ele não atualiza
    Outro caso é caso tenha 1 filme adicionado, retira ele e volte, nesse caso ele crasha
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MoviesCarousel(homeMovies: List<HomeMovie>, goToMovieDetails: (Long) -> Unit) {
    HorizontalMultiBrowseCarousel(
        state = CarouselState(itemCount = { homeMovies.count() }),
        modifier = Modifier
            .width(412.dp)
            .height(221.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
    ) { index ->
        val movie = homeMovies[index]
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
fun NoMoviesFavorite(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.no_movies_favorite),
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
        discoverMoviesList = emptyList(),
        favoriteMoviesList = emptyList(),
        inputQuery = "",
        onInputQueryChange = {},
        navigateToMovieList = { },
        hasInternetConnection = true,
        goToMovieDetails = {}
    )
}
