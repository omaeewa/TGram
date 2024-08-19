package com.miracle.chat.model

import com.miracle.data.model.Chat
import org.drinkless.tdlib.TdApi.ChatPhotoInfo

data class ChatInfo(
    val id: Long,
    val title: String,
    val imageModel: Any?,
    val placeholderRes: Int? = null,
    val canSendMessages: Boolean,
    val lastReadOutboxMessageId: Long = 0
) {
    companion object {
        val empty = ChatInfo(
            id = 0,
            title = "",
            imageModel = null,
            canSendMessages = true
        )

        val dummy = ChatInfo(
            id = 0,
            title = "Telegram",
            imageModel = 12,
            placeholderRes = com.miracle.common.R.drawable.nikolaj_durov,
            canSendMessages = true
        )
    }
}


fun Chat.toChatInfo() = ChatInfo(
    id = id,
    title = title,
    imageModel = photo?.takePhoto(),
    lastReadOutboxMessageId = lastReadOutboxMessageId,
    canSendMessages = permissions.canSendBasicMessages
)

fun ChatPhotoInfo.takePhoto() =
    small?.local?.path?.takeIf { it.isNotEmpty() } ?: minithumbnail?.data
