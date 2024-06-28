package com.miracle.data.mapper

import com.miracle.model.ChatListItem
import org.drinkless.tdlib.TdApi.*

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