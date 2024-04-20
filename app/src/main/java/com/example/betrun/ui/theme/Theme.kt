package com.example.betrun.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Eucalyptus,
    onPrimary = White,
    surface = White,
    onSurface = SpaceCadet,
    onSurfaceVariant = QuickSilver,
    outline = AzureishWhite,
    outlineVariant = Cultured,
    secondary = SeaSerpent,
    background = White,
    onBackground = SpaceCadet,
    tertiary = CelticBlue
)

private val LightColorScheme = lightColorScheme(
    primary = Eucalyptus,
    onPrimary = White,
    surface = White,
    onSurface = SpaceCadet,
    onSurfaceVariant = QuickSilver,
    outline = AzureishWhite,
    outlineVariant = Cultured,
    secondary = SeaSerpent,
    background = White,
    onBackground = SpaceCadet,
    tertiary = CelticBlue
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BetrunTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}