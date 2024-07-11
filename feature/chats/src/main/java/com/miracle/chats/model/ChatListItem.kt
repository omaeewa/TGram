package com.miracle.chats.model

import org.drinkless.tdlib.TdApi.Chat
import org.drinkless.tdlib.TdApi.ChatPhotoInfo
import org.drinkless.tdlib.TdApi.MessageContent
import org.drinkless.tdlib.TdApi.MessageDocument
import org.drinkless.tdlib.TdApi.MessagePhoto
import org.drinkless.tdlib.TdApi.MessageText
import org.drinkless.tdlib.TdApi.MessageUnsupported
import org.drinkless.tdlib.TdApi.MessageVideo

data class ChatListItem(
    val id: Long,
    val title: String,
    val imageModel: Any?,
    val placeholderRes: Int? = null,
    val isMuted: Boolean,
    val unreadCount: Int,
    val lastMessage: String?,
    val date: Int? //Timestamp
) {

    companion object {
        val empty = ChatListItem(
            id = 0,
            title = "",
            imageModel = null,
            isMuted = false,
            unreadCount = 0,
            lastMessage = null,
            date = null
        )

        val dummy = ChatListItem(
            id = 0,
            title = "Telegram",
            imageModel = 12,
            isMuted = false,
            unreadCount = 4,
            lastMessage = "Hello there",
            date = 1376427600,
            placeholderRes = com.miracle.common.R.drawable.nikolaj_durov
        )
    }
}

fun Chat.toChatListItem() = ChatListItem(
    id = id,
    title = title,
    imageModel = photo?.takePhoto(),
    isMuted = notificationSettings.muteFor > 0,
    unreadCount = unreadCount,
    lastMessage = lastMessage?.content.toTextMessage(),
    date = lastMessage?.date,
)

fun ChatPhotoInfo.takePhoto() = small?.local?.path?.takeIf { it.isNotEmpty() } ?: minithumbnail?.data

fun MessageContent?.toTextMessage(): String = when(this) {
    is MessageText -> text.text
    is MessagePhoto -> caption.text.takeIf { it.isNotEmpty() } ?: "Photo"
    is MessageUnsupported -> "Message..."
    is MessageVideo -> caption.text.takeIf { it.isNotEmpty() } ?: "Video"
    is MessageDocument -> document.fileName
    else -> ""
}
