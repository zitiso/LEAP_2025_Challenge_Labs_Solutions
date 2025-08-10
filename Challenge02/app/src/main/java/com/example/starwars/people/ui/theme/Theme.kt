package com.example.starwars.people.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE), // Indigo 500
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBB86FC), // Indigo 200
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF03DAC6), // Teal 200
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF018786), // Teal 700
    onSecondaryContainer = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.White,
    error = Color(0xFFB00020), // Red 700
    onError = Color.White
)

@Composable
fun StarWarsPeopleTheme(
    useDarkTheme: Boolean = false, // Set default to false for light theme
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) {
        darkColorScheme()
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}