package com.miracle.chat.model

import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.MessageContent
import org.drinkless.tdlib.TdApi.MessageDocument
import org.drinkless.tdlib.TdApi.MessagePhoto
import org.drinkless.tdlib.TdApi.MessageText
import org.drinkless.tdlib.TdApi.MessageUnsupported
import org.drinkless.tdlib.TdApi.MessageVideo

data class Message(
    val id: Long,
    val userId: Long?,
    val message: String,
    val date: Int
) {
    companion object {
        val dummy = Message(1, userId = null, message = "Hello brabus", date = 1376427600)
    }
}

fun TdApi.Message.toMessage() = Message(
    id = id,
    userId = userId(),
    message = content.toTextMessage(),
    date = date
)

fun TdApi.Message.userId(): Long? = when (val sender = senderId) {
    is TdApi.MessageSenderUser -> sender.userId
    else -> null
}

fun MessageContent?.toTextMessage(): String = when (this) {
    is MessageText -> text.text
    is MessagePhoto -> caption.text.takeIf { it.isNotEmpty() } ?: "Photo"
    is MessageUnsupported -> "Message..."
    is MessageVideo -> caption.text.takeIf { it.isNotEmpty() } ?: "Video"
    is MessageDocument -> document.fileName
    else -> ""
}