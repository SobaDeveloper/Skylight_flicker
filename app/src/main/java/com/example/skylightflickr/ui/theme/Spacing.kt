package com.example.skylightflickr.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class ComponentSpacing(
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 20.dp,
    val xl: Dp = 24.dp,

    val chatMessageIndent: Dp = 64.dp,
)

val LocalSpacing = staticCompositionLocalOf { ComponentSpacing() }

val Spacing: ComponentSpacing
    @Composable get() = LocalSpacing.current