package com.aetherinsight.goldentomatoes.core.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aetherinsight.goldentomatoes.core.ui.theme.TextColor

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
        painter = painterResource(id = com.aetherinsight.goldentomatoes.core.ui.R.drawable.ic_sad_face),
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