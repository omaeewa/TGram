package com.miracle.chat.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miracle.chat.R
import com.miracle.chat.model.MessageUi
import com.miracle.chat.model.MessageSendStatus
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
    modifier: Modifier = Modifier
) {
    TextWithItemInTheEnd(
        modifier = modifier.padding(7.dp),
        text = content.text.text,
        textStyle = mTypography.bodyLarge,
        textColor = mColors.onSurface,
        endItem = {
            Row(
                modifier = Modifier
                    .offset(2.dp, 3.dp)
                    .padding(start = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date.toTimeString(),
                    style = mTypography.bodyMedium,
                    color = if (messageType.isRightSide()) mColors.onSurface else mColors.secondary,
                    modifier = Modifier.align(Alignment.Bottom)
                )

                if (messageType.isRightSide())
                    SendStatusIcon(
                        sendStatus = sendStatus, Modifier
                            .padding(start = 4.dp)
                            .size(18.dp)
                    )
            }
        }
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
    val messageType = MessageType.Single(Side.Left)
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