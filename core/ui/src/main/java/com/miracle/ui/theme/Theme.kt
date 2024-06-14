package com.miracle.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

interface ColorScheme {
    val surface: Color
    val onSurface: Color
    val onSurfaceVariant: Color
    val primary: Color

    object DarkColorScheme : ColorScheme {
        override val surface = DarkNavy
        override val onSurface = LightSkyBlue
        override val onSurfaceVariant = SteelGray
        override val primary = AquaBlue
    }
//
//    object LightColorScheme : ColorScheme {
//        override val background = Color(1, 1, 1)
//    }
}


@Composable
fun TGramTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    shapes: Shapes = Shapes(),
    spacing: Spacing = Spacing.default,
    content: @Composable () -> Unit
) {
//    val colorScheme = if (darkTheme) ColorScheme.DarkColorScheme else ColorScheme.LightColorScheme
    val colorScheme = ColorScheme.DarkColorScheme

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalTypography provides Typography,
        LocalSpacing provides spacing,
        LocalShapes provides shapes
    ) {
        content()
    }
}

@Composable
fun TGramThemeWithBack(content: @Composable () -> Unit) {
    TGramTheme {
        Box(modifier = Modifier.background(LocalColorScheme.current.surface)) {
            content()
        }
    }
}

val LocalColorScheme = compositionLocalOf<ColorScheme> { error("LocalColorScheme not found!") }
val LocalTypography = compositionLocalOf<Typography> { error("LocalTypography not found!") }
val LocalSpacing = compositionLocalOf<Spacing> { error("LocalSpacing not found!") }
val LocalShapes = compositionLocalOf<Shapes> { error("LocalShapes not found!") }
