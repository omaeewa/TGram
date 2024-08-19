package com.miracle.chat.model

import com.miracle.data.model.FormattedText
import com.miracle.data.model.Message
import com.miracle.data.model.MessageContent
import com.miracle.data.model.MessagePhoto
import com.miracle.data.model.MessagePhotoGroup
import com.miracle.data.model.MessageText
import org.drinkless.tdlib.TdApi

data class MessageUi(
    val id: Long,
    val userId: Long?,
    val messageContent: MessageContent,
    val isOutgoing: Boolean,
    val date: Int,
    val mediaAlbumId: Long = -1
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
    date = date,
    mediaAlbumId = mediaAlbumId
)


fun List<Message>.toMessage(): List<MessageUi> {
    val result = mutableListOf<Message>()
    val iterator = this.listIterator()

    while (iterator.hasNext()) {
        val current = iterator.next()

        if (current.content is MessagePhoto) {
            val group = mutableListOf<Message>()
            group.add(current)

            while (iterator.hasNext()) {
                val next = iterator.next()
                if (next.content is MessagePhoto && next.mediaAlbumId == current.mediaAlbumId && next.mediaAlbumId != 0L) {
                    group.add(next)
                } else {
                    iterator.previous()
                    break
                }
            }

            if (group.size in 2..6) { //TODO only max 6 photos are available so far
                result.add(current.copy(content = MessagePhotoGroup(group.map { it.content as MessagePhoto })))
            } else {
                result.addAll(group)
            }
        } else {
            result.add(current)
        }
    }

    return result.map { it.toMessage() }
}

fun List<Message>.toMessagePhotoGroup() = first().toMessage()
    .copy(messageContent = MessagePhotoGroup(this.mapNotNull { it.content as? MessagePhoto }))

fun Message.userId(): Long? = when (val sender = senderId) {
    is TdApi.MessageSenderUser -> sender.userId
    else -> null
}

enum class MessageSendStatus {
    SENDING,
    SENT_UNREAD,
    SENT_READ,
    SEND_ERROR
}