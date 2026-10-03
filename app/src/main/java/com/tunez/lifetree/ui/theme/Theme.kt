package com.tunez.lifetree.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LifeTreeLightColors = lightColorScheme(
    primary = LightAccent,
    onPrimary = LightSurface,

    secondary = LightAccent,
    onSecondary = LightSurface,

    background = LightBackground,
    onBackground = LightText,

    surface = LightSurface,
    onSurface = LightText,

    surfaceVariant = LightSurfaceSoft,
    onSurfaceVariant = LightTextSecondary,

    outline = LightBorder
)

private val LifeTreeDarkColors = darkColorScheme(
    primary = DarkAccent,
    onPrimary = DarkBackground,

    secondary = DarkAccent,
    onSecondary = DarkBackground,

    background = DarkBackground,
    onBackground = DarkText,

    surface = DarkSurface,
    onSurface = DarkText,

    surfaceVariant = DarkSurfaceSoft,
    onSurfaceVariant = DarkTextSecondary,

    outline = DarkBorder
)

@Composable
fun LifeTreeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        LifeTreeDarkColors
    } else {
        LifeTreeLightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = LifeTreeTypography,
        content = content
    )
}