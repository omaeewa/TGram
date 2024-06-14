package com.miracle.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val tiny: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp
) {
    companion object {
        val default = Spacing(
            tiny = 4.dp,
            small = 8.dp,
            medium = 16.dp,
            large = 24.dp,
            extraLarge = 32.dp
        )
    }
}