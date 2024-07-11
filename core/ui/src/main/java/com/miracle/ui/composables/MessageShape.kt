package com.miracle.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.miracle.ui.composables.MessageShapePath.leftTailed
import com.miracle.ui.composables.MessageShapePath.rightTailed
import com.miracle.ui.composables.MessageShapePath.tailLess
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography


sealed interface MessageType {
    sealed interface Left : MessageType {
        data object Start : Left
        data object Middle : Left
        data object End : Left
        data object Single : Left
    }

    sealed interface Right : MessageType {
        data object Start : Right
        data object Middle : Right
        data object End : Right
        data object Single : Right
    }

    fun isSenderMe() = this is Right
    fun isLeft() = this is Left
}



fun getMessageType(
    previousTimestamp: Int?,
    currentTimestamp: Int,
    nextTimestamp: Int?,
    previousMessageType: MessageType?,
    currentMessageType: MessageType,
    isSameUser: Boolean,
    timeGap: Int = 6 * 60
): MessageType {

    val isPreviousInTimeRange =
        previousTimestamp != null && (currentTimestamp - previousTimestamp <= timeGap)
    val isNextInTimeRange = nextTimestamp != null && (nextTimestamp - currentTimestamp <= timeGap)
    val isPreviousSameSide = previousMessageType == currentMessageType
    val isGroupContinued = isPreviousInTimeRange && isPreviousSameSide && isSameUser

    return when {
        !isGroupContinued -> {
            if (isNextInTimeRange) {
                if (currentMessageType.isSenderMe()) MessageType.Right.Start else MessageType.Left.Start
            } else {
                if (currentMessageType.isSenderMe()) MessageType.Right.Single else MessageType.Left.Single
            }
        }

        isNextInTimeRange -> {
            if (currentMessageType.isSenderMe()) MessageType.Right.Middle else MessageType.Left.Middle
        }

        else -> {
            if (currentMessageType.isSenderMe()) MessageType.Right.End else MessageType.Left.End
        }
    }
}

private const val Big_Corner = 50f
private const val Small_Corner = 15f
const val Gap_Radius = 20f

fun MessageType.getShapePath(drawScope: DrawScope) = when (this) {
    MessageType.Left.Start -> drawScope.tailLess(
        gapRadius = Gap_Radius,
        corners = MessageShapePath.Corners(
            topLeftRadius = Big_Corner,
            topRightRadius = Big_Corner,
            bottomRightRadius = Big_Corner,
            bottomLeftRadius = Small_Corner
        ),
        isGapLeft = true
    )

    MessageType.Left.Middle -> drawScope.tailLess(
        gapRadius = Gap_Radius,
        corners = MessageShapePath.Corners(
            topLeftRadius = Small_Corner,
            topRightRadius = Big_Corner,
            bottomRightRadius = Big_Corner,
            bottomLeftRadius = Small_Corner
        ),
        isGapLeft = true
    )

    MessageType.Left.End -> drawScope.leftTailed(
        gapRadius = Gap_Radius,
        corners = MessageShapePath.Corners(
            topLeftRadius = Small_Corner,
            topRightRadius = Big_Corner,
            bottomRightRadius = Big_Corner
        )
    )

    MessageType.Left.Single -> drawScope.leftTailed(
        gapRadius = Gap_Radius,
        corners = MessageShapePath.Corners(
            topLeftRadius = Big_Corner,
            topRightRadius = Big_Corner,
            bottomRightRadius = Big_Corner
        )
    )

    MessageType.Right.Start -> drawScope.tailLess(
        gapRadius = Gap_Radius,
        corners = MessageShapePath.Corners(
            topLeftRadius = Big_Corner,
            topRightRadius = Big_Corner,
            bottomRightRadius = Small_Corner,
            bottomLeftRadius = Big_Corner
        ),
        isGapLeft = false
    )

    MessageType.Right.Middle -> drawScope.tailLess(
        gapRadius = Gap_Radius,
        corners = MessageShapePath.Corners(
            topLeftRadius = Big_Corner,
            topRightRadius = Small_Corner,
            bottomRightRadius = Small_Corner,
            bottomLeftRadius = Big_Corner
        ),
        isGapLeft = false
    )

    MessageType.Right.End -> drawScope.rightTailed(
        gapRadius = Gap_Radius,
        corners = MessageShapePath.Corners(
            topLeftRadius = Big_Corner,
            topRightRadius = Small_Corner,
            bottomLeftRadius = Big_Corner
        )
    )

    MessageType.Right.Single -> drawScope.rightTailed(
        gapRadius = Gap_Radius,
        corners = MessageShapePath.Corners(
            topLeftRadius = Big_Corner,
            topRightRadius = Big_Corner,
            bottomLeftRadius = Big_Corner
        )
    )
}

private val SmallPadding = 3.dp
private val BigPadding = 8.dp

fun MessageType.getBottomPadding() = when (this) {
    MessageType.Left.End,
    MessageType.Left.Single,
    MessageType.Right.End,
    MessageType.Right.Single -> BigPadding

    else -> SmallPadding
}

object MessageShapePath {
    data class Corners(
        val topLeftRadius: Float = 0f,
        val topRightRadius: Float = 0f,
        val bottomRightRadius: Float = 0f,
        val bottomLeftRadius: Float = 0f
    )

    fun DrawScope.rightTailed(
        gapRadius: Float,
        corners: Corners
    ) = Path().apply {
        val width = size.width
        val height = size.height

        addRoundRect(
            with(corners) {
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = width - gapRadius,
                    bottom = height,
                    topLeftCornerRadius = CornerRadius(topLeftRadius, topLeftRadius),
                    topRightCornerRadius = CornerRadius(topRightRadius, topRightRadius),
                    bottomRightCornerRadius = CornerRadius(0f, 0f),
                    bottomLeftCornerRadius = CornerRadius(bottomLeftRadius, bottomLeftRadius),
                )
            }
        )

        moveTo(width - gapRadius, height - gapRadius)
        arcTo(
            rect = Rect(
                topLeft = Offset(width - gapRadius, height - gapRadius * 2),
                bottomRight = Offset(width + gapRadius, height)
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = -70f,
            forceMoveTo = false
        )

        lineTo(width - gapRadius / 3, height)
        lineTo(width - gapRadius, height)
    }

    fun DrawScope.leftTailed(
        gapRadius: Float,
        corners: Corners
    ) = Path().apply {
        val width = size.width
        val height = size.height

        addRoundRect(
            with(corners) {
                RoundRect(
                    left = gapRadius,
                    top = 0f,
                    right = width,
                    bottom = height,
                    topLeftCornerRadius = CornerRadius(topLeftRadius, topLeftRadius),
                    topRightCornerRadius = CornerRadius(topRightRadius, topRightRadius),
                    bottomRightCornerRadius = CornerRadius(bottomRightRadius, bottomRightRadius),
                    bottomLeftCornerRadius = CornerRadius(0f, 0f),
                )
            }
        )

        moveTo(gapRadius, height - gapRadius)
        arcTo(
            rect = Rect(
                topLeft = Offset(-gapRadius, height - gapRadius * 2),
                bottomRight = Offset(gapRadius, height)
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 70f,
            forceMoveTo = false
        )

        lineTo(gapRadius / 3, height)
        lineTo(gapRadius, height)
    }

    fun DrawScope.tailLess(
        gapRadius: Float,
        isGapLeft: Boolean,
        corners: Corners
    ) = Path().apply {
        val width = size.width
        val height = size.height

        val left = if (isGapLeft) gapRadius else 0f
        val right = if (isGapLeft) width else width - gapRadius
        addRoundRect(
            with(corners) {
                RoundRect(
                    left = left,
                    top = 0f,
                    right = right,
                    bottom = height,
                    topLeftCornerRadius = CornerRadius(topLeftRadius, topLeftRadius),
                    topRightCornerRadius = CornerRadius(topRightRadius, topRightRadius),
                    bottomRightCornerRadius = CornerRadius(bottomRightRadius, bottomRightRadius),
                    bottomLeftCornerRadius = CornerRadius(bottomLeftRadius, bottomLeftRadius),
                )
            }
        )
    }
}

@Composable
fun MessageShape(
    gradientColors: List<Color>,
    messageType: MessageType,
    modifier: Modifier = Modifier,
    innerPadding: Dp = 7.dp,
    content: @Composable BoxScope.() -> Unit
) {
    var position by remember { mutableStateOf(Offset.Zero) }
    val backColor = mColors.surfaceContainer
    val screenHeight =
        with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.roundToPx() }

    val gapDp = with(LocalDensity.current) { Gap_Radius.toDp() }
    fun Modifier.gapPadding() = when (messageType) {
        is MessageType.Left -> padding(start = gapDp)
        is MessageType.Right -> padding(end = gapDp)
    }

    fun Modifier.drawGradient() = this
        .onGloballyPositioned { coordinates ->
            position = coordinates.positionInRoot()
        }
        .drawBehind {
            val gradientBrush = Brush.verticalGradient(
                colors = gradientColors,
                startY = -position.y,
                endY = screenHeight - position.y
            )

            val path = messageType.getShapePath(this)
            drawPath(path = path, brush = gradientBrush)
        }

    fun Modifier.drawWithoutGradient() = this.drawBehind {
        val path = messageType.getShapePath(this)
        drawPath(path = path, color = backColor)
    }

    fun Modifier.drawBackground() = when (messageType) {
        is MessageType.Left -> drawWithoutGradient()
        is MessageType.Right -> drawGradient()
    }

    Box(
        modifier = modifier
            .drawBackground()
            .gapPadding()
            .padding(innerPadding),
        content = content
    )
}


@Preview
@Composable
private fun AllShapesPreview() {
    val gradientColors = listOf(Color(164, 81, 166), Color(101, 113, 247))

    val types = listOf(
        MessageType.Left.Start,
        MessageType.Left.Middle,
        MessageType.Left.End,
        MessageType.Left.Single,
        MessageType.Right.Start,
        MessageType.Right.Middle,
        MessageType.Right.End,
        MessageType.Right.Single,
    )
    TGramTheme {
        Column {
            types.forEach { type ->
                MessageShape(
                    modifier = Modifier.padding(bottom = type.getBottomPadding()),
                    gradientColors = gradientColors,
                    messageType = type,
                ) {
                    Text(
                        text = "Hello world, aboba",
                        style = mTypography.bodyMedium,
                        color = mColors.onSurface,
                    )
                }
            }
        }
    }
}


