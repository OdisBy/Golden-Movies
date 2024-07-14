package com.odisby.goldentomatoes.feature.search.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.core.ui.theme.TextColor
import com.odisby.goldentomatoes.feature.search.R
import com.odisby.goldentomatoes.feature.search.model.Movie
import com.odisby.goldentomatoes.feature.search.ui.MovieSearchListItem


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchViewApp(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        placeholder = {
            Text(text = stringResource(R.string.search_movie_placeholder))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = TextColor,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = TextColor,
                        contentDescription = stringResource(R.string.clear_query)
                    )
                }
            }
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp,
        content = content
    )
}

@Preview
@Composable
private fun SearchViewAppPreview() {
    GoldenTomatoesTheme {
        SearchViewApp(
            query = "Meu Malv",
            onQueryChange = {},
            onSearch = {}
        ) {
            MovieSearchListItem(
                movie = Movie(
                    id = 1,
                    name = "Meu Malvado Favorito 4",
                    rating = 8
                ),
            )
        }
    }
}
