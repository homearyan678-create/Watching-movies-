package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val CineVerseColorScheme = darkColorScheme(
    primary = CinematicBluePrimary,
    onPrimary = CinematicTextPrimary,
    primaryContainer = CinematicBlueVariant,
    onPrimaryContainer = CinematicTextPrimary,
    secondary = CinematicGold,
    onSecondary = CinematicBackground,
    secondaryContainer = CinematicSurfaceVariant,
    onSecondaryContainer = CinematicTextPrimary,
    tertiary = CinematicRedMature,
    onTertiary = CinematicTextPrimary,
    background = CinematicBackground,
    onBackground = CinematicTextPrimary,
    surface = CinematicSurface,
    onSurface = CinematicTextPrimary,
    surfaceVariant = CinematicSurfaceVariant,
    onSurfaceVariant = CinematicTextSecondary,
    outline = CinematicCardBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Use our rich cinematic colors
    content: @Composable () -> Unit
) {
    val colorScheme = CineVerseColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = CinematicBackground.toArgb()
                window.navigationBarColor = CinematicBackground.toArgb()
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = false
                insetsController.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
