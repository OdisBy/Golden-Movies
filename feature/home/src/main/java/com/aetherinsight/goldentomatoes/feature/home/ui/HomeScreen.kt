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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.core.ui.common.TMDBAttribution
import com.aetherinsight.goldentomatoes.core.ui.common.shimmerBrush
import com.aetherinsight.goldentomatoes.core.ui.constants.Constants.RANDOM_MOVIE_ID
import com.aetherinsight.goldentomatoes.core.ui.constants.ListTypes
import com.aetherinsight.goldentomatoes.core.ui.theme.BackgroundColor
import com.aetherinsight.goldentomatoes.core.ui.theme.Primary200
import com.aetherinsight.goldentomatoes.core.ui.theme.Primary900
import com.aetherinsight.goldentomatoes.core.ui.theme.TextColor
import com.aetherinsight.goldentomatoes.feature.home.R
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import com.aetherinsight.goldentomatoes.feature.search_bar.ui.NoMoviesFounded
import com.aetherinsight.goldentomatoes.feature.search_bar.ui.SearchBarRoot

@Composable
fun HomeRoot(
    navigateToMovieList: (ListTypes) -> Unit,
    navigateToDetailsScreen: (Long) -> Unit,
    modifier: Modifier = Modifier,
    hasInternetConnection: Boolean,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val discoverMovies by viewModel.discoverMovies.collectAsStateWithLifecycle()

    val favoriteMovies by viewModel.favoriteMovies.collectAsStateWithLifecycle()


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
            discoverMovies = discoverMovies,
            favoriteMovies = favoriteMovies,
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
    discoverMovies: Resource<List<HomeMovie>>,
    favoriteMovies: Resource<List<HomeMovie>>,
    goToMovieDetails: (Long) -> Unit,
    navigateToMovieList: (ListTypes) -> Unit,
    hasInternetConnection: Boolean,
    modifier: Modifier = Modifier
) {

    var searchBarActive by remember { mutableStateOf(false) }


    Column {
        SearchBarRoot(
            searchBarActive = searchBarActive,
            goToMovieDetails = goToMovieDetails,
            onChangeSearchBarActive = { searchBarActive = it },
        )

        Column(
            modifier = modifier
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            FavoriteMovies(
                goToMovieDetails,
                navigateToMovieList,
                favoriteMovies
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (hasInternetConnection) {
                DiscoverNewMovies(
                    goToMovieDetails,
                    navigateToMovieList,
                    discoverMovies,
                )
            }

            TMDBAttribution()

        }
    }
}


@Composable
private fun DiscoverNewMovies(
    goToMovieDetails: (Long) -> Unit,
    navigateToMovieList: (ListTypes) -> Unit,
    homeMovies: Resource<List<HomeMovie>>,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .semantics { isTraversalGroup = true }
    ) {
        // Title
        RowTextAndGoButton(
            text = stringResource(R.string.discover_movies_title),
            onButtonClick = {
                navigateToMovieList(ListTypes.DISCOVER)
            },
            buttonVisible = homeMovies is Resource.Success && homeMovies.data.size > 3
        )

        Spacer(modifier = Modifier.height(12.dp))

        when (homeMovies) {
            is Resource.Error -> NoMoviesFounded(
                modifier = Modifier.height(
                    200.dp
                )
            )

            is Resource.Loading -> DiscoverCarousel(emptyList(), goToMovieDetails, true)
            is Resource.Success -> DiscoverCarousel(homeMovies.data, goToMovieDetails, false)
        }
    }
}

@Composable
private fun FavoriteMovies(
    goToMovieDetails: (Long) -> Unit,
    navigateToMovieList: (ListTypes) -> Unit,
    favoriteMovies: Resource<List<HomeMovie>>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .semantics { isTraversalGroup = true }
    ) {
        // Title
        RowTextAndGoButton(
            text = stringResource(R.string.favorite_movies_title),
            onButtonClick = {
                navigateToMovieList(ListTypes.FAVORITE)
            },
            buttonVisible = favoriteMovies is Resource.Success && favoriteMovies.data.size > 3
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Carrousel

        when (favoriteMovies) {
            is Resource.Error -> NoMoviesFavorite()
            is Resource.Loading -> FavoriteCarousel(emptyList(), goToMovieDetails, true)
            is Resource.Success -> {
                FavoriteCarousel(favoriteMovies.data, goToMovieDetails, false)
            }
        }
    }
}

@Composable
fun FavoriteCarousel(
    homeMovies: List<HomeMovie>,
    goToMovieDetails: (Long) -> Unit,
    shimmer: Boolean,
) {
    MoviesCarousel(
        homeMovies = homeMovies,
        goToMovieDetails = goToMovieDetails,
        shimmer = shimmer,
    )
}

@Composable
fun DiscoverCarousel(
    homeMovies: List<HomeMovie>,
    goToMovieDetails: (Long) -> Unit,
    shimmer: Boolean
) {
    MoviesCarousel(
        homeMovies = homeMovies,
        goToMovieDetails = goToMovieDetails,
        shimmer = shimmer,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MoviesCarousel(
    homeMovies: List<HomeMovie>,
    goToMovieDetails: (Long) -> Unit,
    shimmer: Boolean
) {
    HorizontalMultiBrowseCarousel(
        state = CarouselState(itemCount = { if (shimmer) 5 else homeMovies.size }),
        modifier = Modifier
            .width(412.dp)
            .height(221.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
    ) { index ->
        val movie = if (shimmer) HomeMovie(12, "", "", "", false) else homeMovies[index]
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(205.dp)
                .maskClip(MaterialTheme.shapes.extraLarge)
                .background(shimmerBrush(showShimmer = shimmer))
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
