package com.miracle.chatinfo.model

import com.miracle.common.R
import com.miracle.data.model.Chat
import org.drinkless.tdlib.TdApi.ChatPhotoInfo

data class ChatInfo(
    val id: Long,
    val title: String,
    val imageModel: Any?,
    val placeholderRes: Int? = null,
    val canSendMessages: Boolean,
    val lastReadOutboxMessageId: Long = 0,
    val phoneNumber: String
) {
    companion object {
        val empty = ChatInfo(
            id = 0,
            title = "",
            imageModel = null,
            canSendMessages = true,
            phoneNumber = ""
        )

        val dummy = ChatInfo(
            id = 0,
            title = "Telegram",
            imageModel = R.drawable.titarenko,
            placeholderRes = R.drawable.titarenko,
            canSendMessages = true,
            phoneNumber = "380635557823"
        )
    }
}


fun Chat.toChatInfo() = ChatInfo(
    id = id,
    title = title,
    imageModel = photo?.takePhoto(),
    lastReadOutboxMessageId = lastReadOutboxMessageId,
    canSendMessages = permissions.canSendBasicMessages,
    phoneNumber = ChatInfo.dummy.phoneNumber
)

fun ChatPhotoInfo.takePhoto() =
    small?.local?.path?.takeIf { it.isNotEmpty() } ?: minithumbnail?.data
