package com.example.design_kit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    background = Color.Black,
)

private val LightColorScheme = lightColorScheme(
    primary = TumbleWeed,
    secondary = TuftsBlue,
    tertiary = SeaSerpent,
    background = Color.White,
)

/**
 * Applies the theme.
 * @param darkTheme True if the dark theme should be applied. As default it's true if the system in
 * in dark mode.
 * @param content A composable on which the theme is applied. In general the whole app.
 */
@Composable
fun TicTacToeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        content = content,
        colorScheme = colorScheme
    )
}
