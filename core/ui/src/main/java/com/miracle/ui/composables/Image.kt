package com.miracle.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.mColors
import android.graphics.Color as AndroidColor

@Composable
fun ProfilePhoto(
    imageModel: Any?,
    profilePhotoSize: ProfilePhotoSize,
    title: String,
    userId: Long,
    modifier: Modifier = Modifier,
    placeholderRes: Int? = null
) {
    if (imageModel == null) ShowInitials(
        modifier = modifier,
        initials = title.firstOrNull()?.uppercase().orEmpty(),
        gradientColors = userId.getGradientColors(),
        profilePhotoSize = profilePhotoSize
    )
    else ShowProfilePhoto(
        imageModel = imageModel,
        modifier = modifier,
        imageSize = profilePhotoSize.imageSize,
        placeholderRes = placeholderRes
    )
}

private fun Long.getGradientColors(factor: Float = 0.8f): List<Color> {
    val hexColor = String.format("#%06X", (0xFFFFFF and this.toInt()))

    val baseColor = AndroidColor.parseColor(hexColor)

    val red = AndroidColor.red(baseColor)
    val green = AndroidColor.green(baseColor)
    val blue = AndroidColor.blue(baseColor)

    val darkColor = AndroidColor.rgb(
        (red * factor).toInt().coerceAtLeast(0),
        (green * factor).toInt().coerceAtLeast(0),
        (blue * factor).toInt().coerceAtLeast(0)
    )

    return listOf(Color(baseColor), Color(darkColor))
}


data class ProfilePhotoSize(val imageSize: Dp, val fontSize: TextUnit) {
    companion object {
        val extraLarge = ProfilePhotoSize(imageSize = 65.dp, fontSize = 22.sp)
        val large = ProfilePhotoSize(imageSize = 55.dp, fontSize = 20.sp)
        val medium = ProfilePhotoSize(imageSize = 45.dp, fontSize = 16.sp)
        val small = ProfilePhotoSize(imageSize = 35.dp, fontSize = 14.sp)
    }
}

@Composable
fun ShowProfilePhoto(
    imageModel: Any,
    imageSize: Dp,
    modifier: Modifier = Modifier,
    placeholderRes: Int? = null
) {
    val placeholderPainter = placeholderRes?.let { painterResource(id = it) }

    AsyncImage(
        model = imageModel,
        contentDescription = null,
        modifier = modifier
            .size(imageSize)
            .clip(CircleShape),
        placeholder = placeholderPainter
    )
}

@Composable
fun ShowInitials(
    initials: String,
    profilePhotoSize: ProfilePhotoSize,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(profilePhotoSize.imageSize)
            .background(Brush.linearGradient(gradientColors), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            modifier = Modifier.align(Alignment.Center),
            fontSize = profilePhotoSize.fontSize,
            fontWeight = FontWeight.Medium,
            color = mColors.onSurface
        )
    }
}

@Preview
@Composable
private fun ProfilePhotoPreview() {
    TGramTheme {
        ProfilePhoto(
            imageModel = null,
            profilePhotoSize = ProfilePhotoSize.large,
            title = "Brabus",
            userId = 827364276
        )
    }
}