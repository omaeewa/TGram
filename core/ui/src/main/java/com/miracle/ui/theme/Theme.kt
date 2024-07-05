package com.miracle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

interface ColorScheme {
    val surface: Color
    val onSurface: Color
    val surfaceContainer: Color
    val background: Color
    val primary: Color
    val primaryContainer: Color
    val secondary: Color
    val secondaryContainer: Color

    object DarkColorScheme : ColorScheme {
        override val surface = DarkBlueGray
        override val onSurface = White
        override val surfaceContainer = DarkSlateGray
        override val background = DarkBlueGray
        override val primary = SkyBlue
        override val primaryContainer = LightSkyBlue
        override val secondary = LightSlateGray
        override val secondaryContainer = SteelBlue
    }
//
//    object LightColorScheme : ColorScheme {
//        override val background = Color(1, 1, 1)
//    }
}

val darkColorScheme = darkColorScheme(
    surface = DarkBlueGray,
    onSurface = White,
    surfaceContainer = DarkSlateGray,
    background = DarkBlueGray,
    primary = SkyBlue,
    primaryContainer = LightSkyBlue,
    secondary = LightSlateGray,
    secondaryContainer = SteelBlue
)


@Composable
fun TGramTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    shapes: Shapes = Shapes(),
    spacing: Spacing = Spacing.default,
    content: @Composable () -> Unit
) {

//    val colorScheme = if (darkTheme) ColorScheme.DarkColorScheme else ColorScheme.LightColorScheme

//    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
//    val colors = when {
//        dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
//        dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
//        else -> darkColorScheme
//    }

    CompositionLocalProvider(
        LocalSpacing provides spacing,
    ) {
        MaterialTheme(
            typography = Typography,
            shapes = shapes,
            colorScheme = darkColorScheme
        ) {
            content()
        }
    }
}


//Material 3 vars
//val mColors @Composable get() = MaterialTheme.colorScheme
val mTypography @Composable get() = MaterialTheme.typography
val mShapes @Composable get() = MaterialTheme.shapes
val mColors @Composable get() = MaterialTheme.colorScheme

//Local vars
val lSpacing @Composable get() = LocalSpacing.current


val LocalSpacing = compositionLocalOf<Spacing> { error("LocalSpacing not found!") }
val LocalColorScheme = compositionLocalOf<ColorScheme> { error("LocalColorScheme not found!") }
