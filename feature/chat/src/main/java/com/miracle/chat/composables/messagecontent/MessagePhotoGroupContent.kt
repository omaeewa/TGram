package com.miracle.chat.composables.messagecontent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.miracle.chat.model.MessageSendStatus
import com.miracle.data.model.MessagePhotoGroup
import com.miracle.data.model.MessageText
import com.miracle.ui.composables.MessageType
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import kotlin.math.roundToInt

@Composable
fun MessagePhotoGroupContent(
    date: Int,
    content: MessagePhotoGroup,
    messageType: MessageType,
    sendStatus: MessageSendStatus,
    modifier: Modifier = Modifier,
) {
    val mainContent = content.messagePhoto[1]
    val containsCaption = mainContent.caption.text.isNotEmpty()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val photoSizes = remember(content) {
        calculatePhotoSizes(maxWidth = screenWidth.value, photosNum = content.messagePhoto.size)
    }


    Column(modifier.padding(1.dp)) {
        Box(Modifier.clip(messageType.imageShape(containsCaption))) {

            CustomFlowLayout {
                photoSizes.forEachIndexed { index, size ->
                    val model = content.messagePhoto[index]
                    val imageSize = model.photo.sizes.lastOrNull()
                    val mainImageModel =
                        imageSize?.photo?.local?.path.takeIf { it?.isNotEmpty() == true }
                    val lowSizeImageModel = model.photo.minithumbnail?.data
                    val lowSizeBlur = if (mainImageModel != null) Modifier else Modifier.blur(20.dp)

                    AsyncImage(
                        modifier = Modifier
                            .then(lowSizeBlur)
                            .size(width = size.width.dp, height = size.height.dp)
                            .padding(1.dp)
                            .clickable { },
                        model = mainImageModel ?: lowSizeImageModel,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            if (!containsCaption)
                Box(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(lSpacing.small)
                        .background(
                            Color.Black.copy(alpha = 0.4f),
                            RoundedCornerShape(50)
                        )
                ) {
                    DateWithSendStatus(
                        date = date,
                        sendStatus = sendStatus,
                        modifier = Modifier.padding(horizontal = lSpacing.tiny),
                        showSendStatusIcon = messageType.isRightSide(),
                        dateTextColor = mColors.onSurface
                    )
                }
        }

        if (containsCaption)
            MessageTextContent(
                date = date,
                content = MessageText(text = mainContent.caption),
                messageType = messageType,
                sendStatus = sendStatus,
                fillParentWidth = true
            )
    }
}



fun calculatePhotoSizes(
    photosNum: Int,
    maxWidth: Float = 300f,
    maxHeight: Float = 400f
) = when (photosNum) {
    2 -> listOf(
        Size(width = maxWidth / 2, height = maxHeight),
        Size(width = maxWidth / 2, height = maxHeight),
    )

    3 -> listOf(
        Size(width = maxWidth * 2 / 3, height = maxHeight),
        Size(width = maxWidth * 1 / 3, height = maxHeight * 1 / 2),
        Size(width = maxWidth * 1 / 3, height = maxHeight * 1 / 2),
    )

    4 -> listOf(
        Size(width = maxWidth * 2 / 3, height = maxHeight),
        Size(width = maxWidth * 1 / 3, height = maxHeight * 1 / 3),
        Size(width = maxWidth * 1 / 3, height = maxHeight * 1 / 3),
        Size(width = maxWidth * 1 / 3, height = maxHeight * 1 / 3),
    )

    5 -> listOf(
        Size(width = maxWidth / 2, height = maxHeight * 2 / 3),
        Size(width = maxWidth / 2, height = maxHeight * 2 / 3),
        Size(width = maxWidth / 3, height = maxHeight * 1 / 3),
        Size(width = maxWidth / 3, height = maxHeight * 1 / 3),
        Size(width = maxWidth / 3, height = maxHeight * 1 / 3),
    )

    6 -> listOf(
        Size(width = maxWidth / 3, height = maxHeight / 2),
        Size(width = maxWidth / 3, height = maxHeight / 2),
        Size(width = maxWidth / 3, height = maxHeight / 2),
        Size(width = maxWidth / 3, height = maxHeight / 2),
        Size(width = maxWidth / 3, height = maxHeight / 2),
        Size(width = maxWidth / 3, height = maxHeight / 2),
    )

    else -> (0 until photosNum).map {
        Size(
            width = maxWidth / 3,
            height = maxHeight / (photosNum % 3 + 1)
        )
    }
}

@Composable
fun CustomFlowLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val positions = when (placeables.size) {
            2 -> listOf(
                0 to 0,
                placeables[0].width to 0,
            )

            3 -> listOf(
                0 to 0,
                placeables[0].width to 0,
                placeables[0].width to placeables[1].height,
            )

            4 -> listOf(
                0 to 0,
                placeables[0].width to 0,
                placeables[0].width to placeables[1].height,
                placeables[0].width to placeables[1].height + placeables[2].height,
            )

            5 -> listOf(
                0 to 0,
                placeables[0].width to 0,
                0 to placeables[0].height,
                placeables[2].width to placeables[0].height,
                placeables[2].width + placeables[3].width to placeables[0].height,
            )

            6 -> listOf(
                0 to 0,
                placeables[0].width to 0,
                placeables[0].width + placeables[1].width to 0,
                0 to placeables[0].height,
                placeables[0].width to placeables[0].height,
                placeables[0].width + placeables[4].width to placeables[0].height,
            )

            else -> emptyList()
        }

        val layoutWidth =
            positions.maxOfOrNull { it.first + placeables[positions.indexOf(it)].width }
                ?: constraints.minWidth
        val layoutHeight =
            positions.maxOfOrNull { it.second + placeables[positions.indexOf(it)].height }
                ?: constraints.minHeight

        layout(layoutWidth, layoutHeight) {
            positions.zip(placeables).forEach { (position, placeable) ->
                placeable.place(position.first, position.second)
            }
        }
    }
}