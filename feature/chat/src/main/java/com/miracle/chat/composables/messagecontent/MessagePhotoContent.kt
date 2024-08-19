package com.miracle.chat.composables.messagecontent

import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.miracle.chat.model.MessageSendStatus
import com.miracle.data.model.FormattedText
import com.miracle.data.model.MessagePhoto
import com.miracle.data.model.MessageText
import com.miracle.ui.composables.MessageShape
import com.miracle.ui.composables.MessageType
import com.miracle.ui.composables.Side
import com.miracle.ui.composables.dummyGradientColors
import com.miracle.ui.composables.getBottomPadding
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors

@Composable
fun MessagePhotoContent(
    date: Int,
    content: MessagePhoto,
    messageType: MessageType,
    sendStatus: MessageSendStatus,
    modifier: Modifier = Modifier,
    placeholderRes: Int? = null
) {
    val placeholder = placeholderRes?.let { painterResource(id = it) }
    val containsCaption = content.caption.text.isNotEmpty()
    val imageSize = content.photo.sizes.lastOrNull()
    val resizedSize =
        resizeImage(originalWidth = imageSize?.width, originalHeight = imageSize?.height)

    val mainImageModel = imageSize?.photo?.local?.path.takeIf { it?.isNotEmpty() == true }

    val lowSizeImageModel = content.photo.minithumbnail?.data
    val lowSizeBlur = if (mainImageModel != null) Modifier else Modifier.blur(20.dp)


    Column(
        modifier
            .padding(2.dp)
            .width(width = resizedSize.width.dp)) {
        Box(
            Modifier
                .clip(messageType.imageShape(containsCaption))
                .width(width = resizedSize.width.dp)
                .height(height = resizedSize.height.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .then(lowSizeBlur)
                    .fillMaxSize(),
                model = mainImageModel ?: lowSizeImageModel,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = placeholder
            )
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
                content = MessageText(text = content.caption),
                messageType = messageType,
                sendStatus = sendStatus,
                fillParentWidth = true
            )
    }
}


fun resizeImage(
    originalWidth: Int?,
    originalHeight: Int?,
    minWidth: Int = 200,
    minHeight: Int = 200,
    maxWidth: Int = 400,
    maxHeight: Int = 400
): Size {
    if (originalWidth == null || originalHeight == null) return Size(maxWidth, maxHeight)

    val aspectRatio = originalWidth.toDouble() / originalHeight

    var newWidth = originalWidth
    var newHeight = originalHeight

    if (newWidth > maxWidth) {
        newWidth = maxWidth
        newHeight = (newWidth / aspectRatio).toInt()
    }
    if (newHeight > maxHeight) {
        newHeight = maxHeight
        newWidth = (newHeight * aspectRatio).toInt()
    }

    if (newWidth < minWidth) {
        newWidth = minWidth
        newHeight = (newWidth / aspectRatio).toInt()
    }
    if (newHeight < minHeight) {
        newHeight = minHeight
        newWidth = (newHeight * aspectRatio).toInt()
    }

    return Size(newWidth, newHeight)
}

fun MessageType.imageShape(containsCation: Boolean = false) = when (val type = this) {
    is MessageType.End -> when (type.side) {
        Side.Left -> RoundedCornerShape(
            topStart = Small_Corner,
            topEnd = Big_Corner,
            bottomEnd = Big_Corner,
            bottomStart = Small_Corner
        )

        Side.Right -> RoundedCornerShape(
            topStart = Big_Corner,
            topEnd = Small_Corner,
            bottomEnd = Small_Corner,
            bottomStart = Big_Corner
        )
    }

    is MessageType.Middle -> when (type.side) {
        Side.Left -> RoundedCornerShape(
            topStart = Small_Corner,
            topEnd = Big_Corner,
            bottomEnd = Big_Corner,
            bottomStart = Small_Corner
        )

        Side.Right -> RoundedCornerShape(
            topStart = Big_Corner,
            topEnd = Small_Corner,
            bottomEnd = Small_Corner,
            bottomStart = Big_Corner
        )
    }

    is MessageType.Single -> when (type.side) {
        Side.Left -> RoundedCornerShape(
            topStart = Big_Corner,
            topEnd = Big_Corner,
            bottomEnd = Big_Corner,
            bottomStart = Small_Corner
        )

        Side.Right -> RoundedCornerShape(
            topStart = Big_Corner,
            topEnd = Big_Corner,
            bottomEnd = Small_Corner,
            bottomStart = Big_Corner
        )
    }

    is MessageType.Start -> when (type.side) {
        Side.Left -> RoundedCornerShape(
            topStart = Big_Corner,
            topEnd = Big_Corner,
            bottomEnd = Big_Corner,
            bottomStart = Small_Corner
        )

        Side.Right -> RoundedCornerShape(
            topStart = Big_Corner,
            topEnd = Big_Corner,
            bottomEnd = Small_Corner,
            bottomStart = Big_Corner
        )
    }
}.let {
    if (containsCation)
        it.copy(
            bottomEnd = CornerSize(Small_Corner),
            bottomStart = CornerSize(Small_Corner)
        )
    else it
}

private const val Big_Corner = 40f
private const val Small_Corner = 15f

@Preview
@Composable
private fun MessagePhotoContentPreviewWithCaption() {
    val type = MessageType.Single(Side.Right)
    TGramTheme {
        MessageShape(
            modifier = Modifier
                .padding(bottom = type.getBottomPadding())
                .widthIn(max = 300.dp),
            gradientColors = dummyGradientColors,
            messageType = type,
        ) {
            MessagePhotoContent(
                content = MessagePhoto(
                    caption = FormattedText(text = "Hello there!")
                ),
                messageType = type,
                placeholderRes = com.miracle.common.R.drawable.titarenko,
                modifier = Modifier.height(400.dp),
                date = 12131,
                sendStatus = MessageSendStatus.SENT_READ
            )
        }
    }
}

@Preview
@Composable
private fun MessagePhotoContentPreview() {
    val type = MessageType.Single(Side.Right)

    TGramTheme {
        MessageShape(
            modifier = Modifier
                .padding(bottom = type.getBottomPadding()),
            gradientColors = dummyGradientColors,
            messageType = type,
        ) {
            MessagePhotoContent(
                content = MessagePhoto(),
                messageType = type,
                placeholderRes = com.miracle.common.R.drawable.titarenko,
                date = 12131,
                sendStatus = MessageSendStatus.SENDING
            )
        }
    }
}