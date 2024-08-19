package com.miracle.chat.composables.messagecontent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miracle.chat.R
import com.miracle.chat.composables.TextWithItemInTheEnd
import com.miracle.chat.model.MessageSendStatus
import com.miracle.chat.model.MessageUi
import com.miracle.common.utils.toTimeString
import com.miracle.data.model.FormattedText
import com.miracle.data.model.MessageText
import com.miracle.ui.composables.MessageShape
import com.miracle.ui.composables.MessageType
import com.miracle.ui.composables.Side
import com.miracle.ui.composables.dummyGradientColors
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography

@Composable
fun MessageTextContent(
    date: Int,
    content: MessageText,
    messageType: MessageType,
    sendStatus: MessageSendStatus,
    modifier: Modifier = Modifier,
    fillParentWidth: Boolean = false
) {
    TextWithItemInTheEnd(
        modifier = modifier.padding(7.dp),
        text = content.text.text,
        textStyle = mTypography.bodyLarge,
        textColor = mColors.onSurface,
        fillParentWidth = fillParentWidth,
        endItem = {
            DateWithSendStatus(
                date = date,
                messageType = messageType,
                sendStatus = sendStatus,
                modifier = Modifier
                    .offset(2.dp, 3.dp)
                    .padding(start = 6.dp)
            )
        }
    )
}

@Composable
fun DateWithSendStatus(
    date: Int,
    dateTextColor: Color,
    showSendStatusIcon: Boolean,
    sendStatus: MessageSendStatus,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date.toTimeString(),
            style = mTypography.bodyMedium,
            color = dateTextColor,
            modifier = Modifier.align(Alignment.Bottom)
        )

        if (showSendStatusIcon)
            SendStatusIcon(
                sendStatus = sendStatus, Modifier
                    .padding(start = 4.dp)
                    .size(18.dp)
            )
    }
}

@Composable
fun DateWithSendStatus(
    date: Int,
    messageType: MessageType,
    sendStatus: MessageSendStatus,
    modifier: Modifier = Modifier
) {

    DateWithSendStatus(
        date = date,
        dateTextColor = if (messageType.isRightSide()) mColors.onSurface else mColors.secondary,
        showSendStatusIcon = messageType.isRightSide(),
        sendStatus = sendStatus,
        modifier = modifier
    )
}


@Composable
private fun SendStatusIcon(sendStatus: MessageSendStatus, modifier: Modifier = Modifier) {
    val iconRes = when (sendStatus) {
        MessageSendStatus.SENDING -> R.drawable.msg_recent
        MessageSendStatus.SENT_UNREAD -> R.drawable.msg_text_check
        MessageSendStatus.SENT_READ -> R.drawable.msg_seen
        MessageSendStatus.SEND_ERROR -> R.drawable.msg_warning
    }

    Icon(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = modifier,
        tint = mColors.onSurface
    )
}

@Preview
@Composable
private fun MessageTextContentPreview() {
    val messageType = MessageType.Single(Side.Right)
    TGramTheme {
        MessageShape(
            gradientColors = dummyGradientColors,
            messageType = messageType,
        ) {

            MessageTextContent(
                date = MessageUi.dummy.date,
                content = MessageText(text = FormattedText(text = "Hello world")),
                messageType = messageType,
                sendStatus = MessageSendStatus.SENT_READ
            )
        }
    }
}