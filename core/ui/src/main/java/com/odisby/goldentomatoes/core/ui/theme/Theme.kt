package com.odisby.goldentomatoes.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Primary600,
    secondary = Secondary800,
    tertiary = TertiaryMain,
    background = BackgroundColor
)

@Composable
fun GoldenTomatoesTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}