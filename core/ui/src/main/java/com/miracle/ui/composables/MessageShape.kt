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
import androidx.compose.ui.unit.dp
import com.miracle.ui.composables.MessageShapePath.leftTailed
import com.miracle.ui.composables.MessageShapePath.rightTailed
import com.miracle.ui.composables.MessageShapePath.tailLess
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography


enum class Side {
    Left, Right
}


sealed class MessageType(val side: Side) {
    class Start(side: Side) : MessageType(side)
    class Middle(side: Side) : MessageType(side)
    class End(side: Side) : MessageType(side)
    class Single(side: Side) : MessageType(side)

    fun isRightSide() = side == Side.Right
}

fun getMessageType(
    previousTimestamp: Int?,
    currentTimestamp: Int,
    nextTimestamp: Int?,
    currentMessageSide: Side,
    isPrevSameUser: Boolean,
    isNextSameUser: Boolean,
    timeGap: Int = 6 * 60
): MessageType {

    val isPreviousInTimeRange =
        previousTimestamp != null && (currentTimestamp - previousTimestamp <= timeGap)
    val isNextInTimeRange = nextTimestamp != null && (nextTimestamp - currentTimestamp <= timeGap)
    val isGroupContinued = isPreviousInTimeRange && isPrevSameUser

    return when {
        !isGroupContinued -> {
            if (isNextInTimeRange && isNextSameUser) MessageType.Start(currentMessageSide)
            else MessageType.Single(currentMessageSide)
        }

        isNextInTimeRange && isNextSameUser -> MessageType.Middle(currentMessageSide)
        else -> MessageType.End(currentMessageSide)
    }
}

private const val Big_Corner = 40f
private const val Small_Corner = 15f
const val Gap_Radius = 20f

fun MessageType.getShapePath(drawScope: DrawScope) = when (val type = this) {
    is MessageType.End -> {
        when (type.side) {
            Side.Left -> drawScope.leftTailed(
                gapRadius = Gap_Radius,
                corners = MessageShapePath.Corners(
                    topLeftRadius = Small_Corner,
                    topRightRadius = Big_Corner,
                    bottomRightRadius = Big_Corner
                )
            )

            Side.Right -> drawScope.rightTailed(
                gapRadius = Gap_Radius,
                corners = MessageShapePath.Corners(
                    topLeftRadius = Big_Corner,
                    topRightRadius = Small_Corner,
                    bottomLeftRadius = Big_Corner
                )
            )
        }
    }

    is MessageType.Middle -> {
        when (type.side) {
            Side.Left -> drawScope.tailLess(
                gapRadius = Gap_Radius,
                corners = MessageShapePath.Corners(
                    topLeftRadius = Small_Corner,
                    topRightRadius = Big_Corner,
                    bottomRightRadius = Big_Corner,
                    bottomLeftRadius = Small_Corner
                ),
                isGapLeft = true
            )

            Side.Right -> drawScope.tailLess(
                gapRadius = Gap_Radius,
                corners = MessageShapePath.Corners(
                    topLeftRadius = Big_Corner,
                    topRightRadius = Small_Corner,
                    bottomRightRadius = Small_Corner,
                    bottomLeftRadius = Big_Corner
                ),
                isGapLeft = false
            )
        }
    }

    is MessageType.Single -> {
        when (type.side) {
            Side.Left -> drawScope.leftTailed(
                gapRadius = Gap_Radius,
                corners = MessageShapePath.Corners(
                    topLeftRadius = Big_Corner,
                    topRightRadius = Big_Corner,
                    bottomRightRadius = Big_Corner
                )
            )

            Side.Right -> drawScope.rightTailed(
                gapRadius = Gap_Radius,
                corners = MessageShapePath.Corners(
                    topLeftRadius = Big_Corner,
                    topRightRadius = Big_Corner,
                    bottomLeftRadius = Big_Corner
                )
            )
        }
    }

    is MessageType.Start -> {
        when (type.side) {
            Side.Left -> drawScope.tailLess(
                gapRadius = Gap_Radius,
                corners = MessageShapePath.Corners(
                    topLeftRadius = Big_Corner,
                    topRightRadius = Big_Corner,
                    bottomRightRadius = Big_Corner,
                    bottomLeftRadius = Small_Corner
                ),
                isGapLeft = true
            )

            Side.Right -> drawScope.tailLess(
                gapRadius = Gap_Radius,
                corners = MessageShapePath.Corners(
                    topLeftRadius = Big_Corner,
                    topRightRadius = Big_Corner,
                    bottomRightRadius = Small_Corner,
                    bottomLeftRadius = Big_Corner
                ),
                isGapLeft = false
            )
        }
    }
}

private val SmallPadding = 3.dp
private val BigPadding = 8.dp


fun MessageType.getBottomPadding() = when (this) {
    is MessageType.End,
    is MessageType.Single -> BigPadding

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

val dummyGradientColors = listOf(
    Color(165, 81, 167, 255),
    Color(126, 70, 193, 255),
    Color(101, 113, 247, 255)
)

@Composable
fun MessageShape(
    gradientColors: List<Color>,
    messageType: MessageType,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    var position by remember { mutableStateOf(Offset.Zero) }
    val backColor = mColors.surfaceContainer
    val screenHeight =
        with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.roundToPx() }

    val gapDp = with(LocalDensity.current) { Gap_Radius.toDp() }
    fun Modifier.gapPadding() = when (messageType.side) {
        Side.Left -> padding(start = gapDp)
        Side.Right -> padding(end = gapDp)
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

    fun Modifier.drawBackground() = when (messageType.side) {
        Side.Left -> drawWithoutGradient()
        Side.Right -> drawGradient()
    }

    Box(
        modifier = modifier
            .drawBackground()
            .gapPadding(),
        content = content
    )
}


@Preview
@Composable
private fun AllShapesPreview() {
    val types = Side.entries.flatMap { side ->
        listOf(
            MessageType.Start(side),
            MessageType.Middle(side),
            MessageType.End(side),
            MessageType.Single(side)
        )
    }

    TGramTheme {
        Column {
            types.forEach { type ->
                MessageShape(
                    modifier = Modifier.padding(bottom = type.getBottomPadding()),
                    gradientColors = dummyGradientColors,
                    messageType = type,
                ) {
                    Text(
                        text = "Hello world, aboba",
                        style = mTypography.bodyMedium,
                        color = mColors.onSurface,
                        modifier = Modifier.padding(7.dp)
                    )
                }
            }
        }
    }
}


