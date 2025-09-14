package com.fyndr.fileshare.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1B3A75),
    onPrimaryContainer = Color(0xFFB8D1FF),

    secondary = SecondaryTeal,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF004D40),
    onSecondaryContainer = Color(0xFFA7F3D0),

    tertiary = TertiaryOrange,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF7A4F00),
    onTertiaryContainer = Color(0xFFFFDCAE),

    error = ErrorRed,
    onError = Color.White,
    errorContainer = Color(0xFF6B1414),
    onErrorContainer = Color(0xFFFFDADA),

    background = DarkBackground,
    onBackground = DarkOnBackground,

    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFCACACA),

    outline = Color(0xFF404040),
    outlineVariant = Color(0xFF2A2A2A),

    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFF1B5AA0)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1B5AA0),
    onPrimary = Color.White,
    primaryContainer = PrimaryBlue,
    onPrimaryContainer = Color.White,

    secondary = Color(0xFF006B5D),
    onSecondary = Color.White,
    secondaryContainer = SecondaryTeal,
    onSecondaryContainer = Color.Black,

    tertiary = Color(0xFFB8860B),
    onTertiary = Color.White,
    tertiaryContainer = TertiaryOrange,
    onTertiaryContainer = Color.Black,

    error = Color(0xFFD32F2F),
    onError = Color.White,
    errorContainer = ErrorRed,
    onErrorContainer = Color.White,

    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),

    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF4F0F4),
    onSurfaceVariant = Color(0xFF49454F),

    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),

    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),
    inversePrimary = PrimaryBlue
)

@Composable
fun FileShareTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
