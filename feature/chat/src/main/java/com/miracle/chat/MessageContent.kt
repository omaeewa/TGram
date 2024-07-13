package com.miracle.chat

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
import com.miracle.chat.model.Message
import com.miracle.common.utils.toTimeString
import com.miracle.ui.composables.MessageShape
import com.miracle.ui.composables.MessageType
import com.miracle.ui.composables.Side
import com.miracle.ui.composables.dummyGradientColors
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography

@Composable
fun MessageTextContent(
    message: Message,
    messageType: MessageType,
    modifier: Modifier = Modifier
) {
    TextWithItemInTheEnd(
        modifier = modifier.padding(7.dp),
        text = message.message,
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
                    text = message.date.toTimeString(),
                    style = mTypography.bodyMedium,
                    color = if (messageType.isRightSide()) mColors.onSurface else mColors.secondary,
                    modifier = Modifier.align(Alignment.Bottom)
                )

                if (messageType.isRightSide())
                    Icon(
                        painter = painterResource(id = R.drawable.msg_mini_checks),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(18.dp),
                        tint = mColors.onSurface
                    )
            }
        }
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

            MessageTextContent(message = Message.dummy, messageType = messageType)
        }
    }
}