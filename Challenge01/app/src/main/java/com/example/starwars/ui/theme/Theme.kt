package com.example.starwars.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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
fun StarWarsTheme(
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
