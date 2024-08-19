package com.miracle.data.updateshandler

import com.miracle.common.di.ApplicationScope
import com.miracle.data.model.Chat
import com.miracle.data.model.toChat
import com.miracle.data.repository.downloadFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.flows.chatAddedToListFlow
import kotlinx.telegram.flows.chatDraftMessageFlow
import kotlinx.telegram.flows.chatLastMessageFlow
import kotlinx.telegram.flows.chatPhotoFlow
import kotlinx.telegram.flows.chatPositionFlow
import kotlinx.telegram.flows.chatReadOutboxFlow
import kotlinx.telegram.flows.chatTitleFlow
import kotlinx.telegram.flows.fileFlow
import kotlinx.telegram.flows.newChatFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.File
import org.drinkless.tdlib.TdApi.UpdateChatAddedToList
import org.drinkless.tdlib.TdApi.UpdateChatDraftMessage
import org.drinkless.tdlib.TdApi.UpdateChatLastMessage
import org.drinkless.tdlib.TdApi.UpdateChatPhoto
import org.drinkless.tdlib.TdApi.UpdateChatPosition
import org.drinkless.tdlib.TdApi.UpdateChatReadOutbox
import org.drinkless.tdlib.TdApi.UpdateChatTitle
import javax.inject.Inject

interface ChatsUpdateHandler {
    val chatsWithUpdates: Flow<List<Chat>>
}

class ChatsUpdateHandlerTdLib @Inject constructor(
    @ApplicationScope private val coroutineScope: CoroutineScope,
    private val telegramApi: TelegramFlow,


    ) : ChatsUpdateHandler {
    override val chatsWithUpdates = merge(
        telegramApi.newChatFlow().map(::handleNewChat),
        telegramApi.chatTitleFlow().map(::handleChatTitleUpdate),
        telegramApi.chatPhotoFlow().map(::handleChatPhotoUpdate),
        telegramApi.chatPositionFlow().map(::handleChatPositions),
        telegramApi.chatLastMessageFlow().map(::handleChatLastMessage),
        telegramApi.fileFlow().map(::handleFileUpdate),
        telegramApi.chatDraftMessageFlow().map(::handleChatDraftMessage),
        telegramApi.chatReadOutboxFlow().map(::handleChatReadOutbox),
        telegramApi.chatAddedToListFlow().map(::handleChatAddedToList)
    ).scan(emptyList<Chat>()) { chats, updateHandler ->
        updateHandler(chats.toMutableList())
    }


    private fun handleNewChat(chat: TdApi.Chat) = UpdateHandler { chats ->
        coroutineScope.launch {
            telegramApi.downloadFile(chat.photo?.small)
        }

        chats + chat.toChat()
    }

    private fun handleChatAddedToList(update: UpdateChatAddedToList) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(chatLists = chat.chatLists + update.chatList)
        }
    }


    private fun handleChatReadOutbox(update: UpdateChatReadOutbox) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(lastReadOutboxMessageId = update.lastReadOutboxMessageId)
        }
    }

    private fun handleChatTitleUpdate(update: UpdateChatTitle) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(title = update.title)
        }
    }

    private fun handleChatPhotoUpdate(update: UpdateChatPhoto) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(photo = update.photo)
        }
    }

    private fun handleChatPositions(update: UpdateChatPosition) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(positions = chat.positions + update.position,)
        }
    }

    private fun handleChatLastMessage(update: UpdateChatLastMessage) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(
                positions = update.positions.toList(),
                lastMessage = update.lastMessage
            )
        }
    }

    private fun handleFileUpdate(file: File) = UpdateHandler { chats ->
        val chatId = chats.find { it.photo?.small?.id == file.id }?.id
        chats.updateChat(chatId) { chat ->
            chat.copy(
                photo = chat.photo.apply {
                    this?.small = file
                }
            )
        }
    }

    private fun handleChatDraftMessage(update: UpdateChatDraftMessage) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(draftMessage = update.draftMessage)
        }
    }

    private fun MutableList<Chat>.updateChat(chatId: Long?, updateChat: (Chat) -> Chat) =
        if (chatId == null) this else this.map { chat ->
            if (chat.id == chatId) updateChat(chat)
            else chat
        }

    fun interface UpdateHandler {
        operator fun invoke(chats: MutableList<Chat>): List<Chat>
    }
}