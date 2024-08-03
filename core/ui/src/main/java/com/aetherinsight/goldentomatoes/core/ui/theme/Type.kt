package com.aetherinsight.goldentomatoes.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aetherinsight.goldentomatoes.core.ui.R

val robotoFlexFamily = FontFamily(
    Font(
        R.font.robotoflex_variable,
    )
)

val Typography = Typography(
    // Subtitle
    bodyLarge = TextStyle(
        fontFamily = robotoFlexFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Body Small Thin
    bodySmall = TextStyle(
        fontFamily = robotoFlexFamily,
        fontWeight = FontWeight.Thin,
        fontSize = 12.sp,
    ),
    // Body
    bodyMedium = TextStyle(
        fontFamily = robotoFlexFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    // Caption
    labelSmall = TextStyle(
        fontFamily = robotoFlexFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    // H1
    headlineLarge = TextStyle(
        fontFamily = robotoFlexFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp
    ),
    // H4
    headlineMedium = TextStyle(
        fontFamily = robotoFlexFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp
    ),
    // H6
    headlineSmall = TextStyle(
        fontFamily = robotoFlexFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
)

