package com.miracle.data.updateshandler

import com.miracle.common.di.ApplicationScope
import com.miracle.data.model.Chat
import com.miracle.data.model.Message
import com.miracle.data.model.MessagePhoto
import com.miracle.data.model.toMessage
import com.miracle.data.repository.downloadFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.flows.deleteMessagesFlow
import kotlinx.telegram.flows.fileFlow
import kotlinx.telegram.flows.newMessageFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.File
import org.drinkless.tdlib.TdApi.UpdateDeleteMessages
import javax.inject.Inject

interface MessagesUpdateHandler {
    suspend fun handleMessageUpdates(chatId: Long): Flow<MessagesUpdateHandlerTdLib.MessagesUpdateHandler>
}

class MessagesUpdateHandlerTdLib @Inject constructor(
    private val telegramApi: TelegramFlow
) : MessagesUpdateHandler {

    override suspend fun handleMessageUpdates(chatId: Long) =
        merge(
            telegramApi.fileFlow().map(::handleFileUpdate),
            telegramApi.newMessageFlow().map { handleNewMessageUpdate(it, chatId) },
            telegramApi.deleteMessagesFlow().map { handleDeletedMessagesUpdate(it, chatId) }
        )


    private fun handleFileUpdate(file: File) = MessagesUpdateHandler { messages ->
        messages.map { message ->
            if ((message.content as? MessagePhoto)?.photo?.sizes?.lastOrNull()?.photo?.id == file.id) {
                message.copy(content = message.content.copy(photo = message.content.photo.copy(
                    sizes = message.content.photo.sizes.map { size ->
                        if (size.photo.id == file.id) size.copy(photo = file) else size
                    }
                )))
            } else {
                message
            }
        }
    }

    private fun handleNewMessageUpdate(newMessage: TdApi.Message, chatId: Long) =
        MessagesUpdateHandler { messages ->
            if (newMessage.chatId == chatId) {
                val existingMessageIndex =
                    messages.indexOfFirst { it.id == newMessage.id }
                if (existingMessageIndex != -1) {
                    messages.toMutableList().apply {
                        set(existingMessageIndex, newMessage.toMessage())
                    }
                } else {
                    listOf(newMessage.toMessage()) + messages
                }
            } else messages
        }


    private fun handleDeletedMessagesUpdate(update: UpdateDeleteMessages, chatId: Long) =
        MessagesUpdateHandler { messages ->
            if (update.chatId == chatId) {
                messages.filterNot { it.id in update.messageIds }
            } else messages
        }

    fun interface MessagesUpdateHandler {
        operator fun invoke(chats: MutableList<Message>): List<Message>
    }
}