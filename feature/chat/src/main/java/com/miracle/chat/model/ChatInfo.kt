package com.miracle.chat.model

import org.drinkless.tdlib.TdApi.Chat
import org.drinkless.tdlib.TdApi.ChatPhotoInfo

data class ChatInfo(
    val id: Long,
    val title: String,
    val imageModel: Any?,
    val placeholderRes: Int? = null,
    val currentUserId: Long
) {
    companion object {
        val empty = ChatInfo(
            id = 0,
            title = "",
            imageModel = null,
            currentUserId = 0
        )

        val dummy = ChatInfo(
            id = 0,
            title = "Telegram",
            imageModel = 12,
            placeholderRes = com.miracle.common.R.drawable.nikolaj_durov,
            currentUserId = 0
        )
    }
}


fun Chat.toChatInfo(currentUserId: Long) = ChatInfo(
    id = id,
    title = title,
    imageModel = photo?.takePhoto(),
    currentUserId = currentUserId
)

fun ChatPhotoInfo.takePhoto() =
    small?.local?.path?.takeIf { it.isNotEmpty() } ?: minithumbnail?.data
