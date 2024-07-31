package com.miracle.chat.model

import com.miracle.data.model.FormattedText
import com.miracle.data.model.Message
import com.miracle.data.model.MessageContent
import com.miracle.data.model.MessageText
import org.drinkless.tdlib.TdApi
data class MessageUi(
    val id: Long,
    val userId: Long?,
    val messageContent: MessageContent,
    val isOutgoing: Boolean,
    val date: Int
) {
    companion object {
        val dummy = MessageUi(
            1,
            userId = null,
            messageContent = MessageText(text = FormattedText("Hello world")),
            isOutgoing = true,
            date = 1376427600
        )
    }
}

fun Message.toMessage() = MessageUi(
    id = id,
    userId = userId(),
    messageContent = content,
    isOutgoing = isOutgoing,
    date = date
)

fun Message.userId(): Long? = when (val sender = senderId) {
    is TdApi.MessageSenderUser -> sender.userId
    else -> null
}

//fun MessageContent?.toTextMessage(): String = when (this) {
//    is MessageText -> text.text
//    is MessagePhoto -> caption.text.takeIf { it.isNotEmpty() } ?: "Photo"
//    is MessageUnsupported -> "Message..."
//    is MessageVideo -> caption.text.takeIf { it.isNotEmpty() } ?: "Video"
//    is MessageDocument -> document.fileName
//    else -> ""
//}

enum class MessageSendStatus {
    SENDING,
    SENT_UNREAD,
    SENT_READ,
    SEND_ERROR
}