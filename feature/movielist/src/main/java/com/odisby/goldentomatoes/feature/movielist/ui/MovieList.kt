package com.odisby.goldentomatoes.feature.movielist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odisby.goldentomatoes.core.ui.theme.BackgroundColor
import com.odisby.goldentomatoes.core.ui.theme.Black_50
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.movielist.R


@Composable
fun MovieListRoot(
    navigateUp: () -> Unit,
    navigateToDetailsScreen: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = MovieListViewModel()
) {
    Scaffold(
        modifier = Modifier
            .background(BackgroundColor)
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            IconButton(
                onClick = navigateUp,
                modifier = Modifier.statusBarsPadding(),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = TextColor,
                    containerColor = Black_50,
                )
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                    contentDescription = "Voltar"
                )
            }
        }
    ) { contentPadding ->
        MovieListScreen(
            uiState = MovieListUiState(),
            navigateToDetailsScreen = navigateToDetailsScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        )
    }
}

@Composable
fun MovieListScreen(
    uiState: MovieListUiState,
    navigateToDetailsScreen: (Long) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(110.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp), //16 dp for small screen
            verticalItemSpacing = 16.dp,
        ) {
            item {
                Column() {
                    Image(
                        painter = painterResource(R.drawable.test_banner),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Meu Malvado Favorito 4",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            item {
                Column() {
                    Image(
                        painter = painterResource(R.drawable.test_banner),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Meu Malvado Favorito 4",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            item {
                Column() {
                    Image(
                        painter = painterResource(R.drawable.test_banner),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Meu Malvado Favorito 4",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            item {
                Column() {
                    Image(
                        painter = painterResource(R.drawable.test_banner),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Meu Malvado Favorito 4",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            item {
                Column() {
                    Image(
                        painter = painterResource(R.drawable.test_banner),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Meu Malvado Favorito 4",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            item {
                Column() {
                    Image(
                        painter = painterResource(R.drawable.test_banner),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Meu Malvado Favorito 4",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


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
                IconButton(
                    onClick = { },
                    modifier = Modifier.statusBarsPadding(),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = TextColor,
                        containerColor = Black_50,
                    )
                ) {
                    Icon(
                        painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                        contentDescription = "Voltar"
                    )
                }
            }
        ) { contentPadding ->
            MovieListScreen(
                uiState = MovieListUiState(),
                navigateToDetailsScreen = { },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            )
        }
    }
}