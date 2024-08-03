package com.aetherinsight.goldentomatoes.core.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aetherinsight.goldentomatoes.core.ui.R

@Composable
fun TMDBAttribution(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TMDBLogo()
        TMDBTextAttribution()
    }
}

@Composable
fun TMDBLogo(modifier: Modifier = Modifier) {
    val rememberLogo = rememberVectorPainter(
        image = ImageVector.vectorResource(id = R.drawable.tmdb_logo)
    )
    Image(
        painter = rememberLogo,
        contentDescription = stringResource(R.string.tmdb_logo_description),
        modifier = modifier.size(60.dp),
    )
}

@Composable
fun TMDBTextAttribution(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.tmdb_attribution_text),
        modifier = modifier.padding(start = 8.dp),
        fontSize = 12.sp,
        lineHeight = 12.sp
    )
}

@Preview
@Composable
private fun AttributionPreview() {
    TMDBAttribution()
}