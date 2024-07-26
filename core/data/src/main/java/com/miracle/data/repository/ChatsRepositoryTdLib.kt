package com.miracle.data.repository

import com.miracle.common.Dispatcher
import com.miracle.common.TGramDispatchers.IO
import com.miracle.common.di.ApplicationScope
import com.miracle.data.model.Chat
import com.miracle.data.model.toChat
import com.miracle.data.repository.ChatsRepositoryTdLib.UpdateHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.downloadFile
import kotlinx.telegram.coroutines.loadChats
import kotlinx.telegram.flows.chatDraftMessageFlow
import kotlinx.telegram.flows.chatLastMessageFlow
import kotlinx.telegram.flows.chatPhotoFlow
import kotlinx.telegram.flows.chatPositionFlow
import kotlinx.telegram.flows.chatTitleFlow
import kotlinx.telegram.flows.fileFlow
import kotlinx.telegram.flows.newChatFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.File
import org.drinkless.tdlib.TdApi.UpdateChatDraftMessage
import org.drinkless.tdlib.TdApi.UpdateChatLastMessage
import org.drinkless.tdlib.TdApi.UpdateChatPhoto
import org.drinkless.tdlib.TdApi.UpdateChatPosition
import org.drinkless.tdlib.TdApi.UpdateChatTitle
import javax.inject.Inject

class ChatsRepositoryTdLib @Inject constructor(
    private val telegramApi: TelegramFlow,
    @Dispatcher(IO) private val dispatcherIo: CoroutineDispatcher,
    @ApplicationScope private val coroutineScope: CoroutineScope
) : ChatsRepository {

    override val chats = merge(
        telegramApi.newChatFlow().map(::handleNewChat),
        telegramApi.chatTitleFlow().map(::handleChatTitleUpdate),
        telegramApi.chatPhotoFlow().map(::handleChatPhotoUpdate),
        telegramApi.chatPositionFlow().map(::handleChatPositions),
        telegramApi.chatLastMessageFlow().map(::handleChatLastMessage),
        telegramApi.fileFlow().map(::handleFileUpdate),
        telegramApi.chatDraftMessageFlow().map(::handleChatDraftMessage)
    ).scan(emptyList<Chat>()) { chats, updateHandler ->
        updateHandler(chats.toMutableList())
    }
        .stateIn(
            coroutineScope,
            SharingStarted.Eagerly,
            emptyList()
        )


    override suspend fun loadMore(limit: Int) = try {
        telegramApi.loadChats(null, limit)
    } catch (e: Throwable) {
        //All chats were loaded
    }

    private fun handleNewChat(chat: TdApi.Chat) = UpdateHandler { chats ->
        chat.photo?.small?.run {
            if (local.canBeDownloaded && !local.isDownloadingActive && !local.isDownloadingCompleted)
                saveImageLocally(id)
        }

        chats + chat.toChat()
    }

    private fun saveImageLocally(fileId: Int) = coroutineScope.launch(dispatcherIo) {
        telegramApi.downloadFile(fileId, 1, 0, 0, true)
    }

    private fun handleChatTitleUpdate(update: UpdateChatTitle) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(
                title = update.title
            )
        }
    }

    private fun handleChatPhotoUpdate(update: UpdateChatPhoto) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(
                photo = update.photo
            )
        }
    }

    private fun handleChatPositions(update: UpdateChatPosition) = UpdateHandler { chats ->
        chats.updateChat(update.chatId) { chat ->
            chat.copy(
                positions = chat.positions + update.position,
            )
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