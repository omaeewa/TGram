package com.miracle.data.repository

import com.miracle.common.Dispatcher
import com.miracle.common.TGramDispatchers.IO
import com.miracle.common.di.ApplicationScope
import com.miracle.data.mapper.toChatListItem
import com.miracle.data.repository.ChatsRepositoryTdLib.UpdateHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.downloadFile
import kotlinx.telegram.coroutines.loadChats
import kotlinx.telegram.flows.chatLastMessageFlow
import kotlinx.telegram.flows.chatPhotoFlow
import kotlinx.telegram.flows.chatPositionFlow
import kotlinx.telegram.flows.chatTitleFlow
import kotlinx.telegram.flows.fileFlow
import kotlinx.telegram.flows.newChatFlow
import org.drinkless.tdlib.TdApi.Chat
import org.drinkless.tdlib.TdApi.File
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
        telegramApi.fileFlow().map(::handleFileUpdate)
    ).scan(emptyList<Chat>()) { chats, updateHandler ->
        chats.toMutableList().apply(updateHandler::invoke)
            .sortedByDescending { it.lastMessage?.date }
    }.map { it.map { chat -> chat.toChatListItem() } }
        .stateIn(coroutineScope, SharingStarted.Lazily, emptyList())


    override suspend fun loadMore(limit: Int): Unit = withContext(dispatcherIo) {
        try {
            telegramApi.loadChats(null, limit)
        } catch (e: Throwable) {
            //All chats were loaded
        }
    }

    private fun handleNewChat(chat: Chat) = UpdateHandler { chats ->
        chats.add(chat)

        chat.photo?.small?.run {
//            coroutineScope.launch(dispatcherIo) { telegramApi.deleteFile(id) }
//            return@run

            if (local.canBeDownloaded && !local.isDownloadingActive && !local.isDownloadingCompleted)
                saveImageLocally(id)
        }
    }

    private fun saveImageLocally(fileId: Int) = coroutineScope.launch(dispatcherIo) {
        telegramApi.downloadFile(fileId, 1, 0, 0, true)
    }

    private fun handleChatTitleUpdate(update: UpdateChatTitle) = UpdateHandler { chats ->
        chats.find { it.id == update.chatId }?.title = update.title
    }

    private fun handleChatPhotoUpdate(update: UpdateChatPhoto) = UpdateHandler { chats ->
        chats.find { it.id == update.chatId }?.photo = update.photo
    }

    private fun handleChatPositions(update: UpdateChatPosition) = UpdateHandler { chats ->
        chats.find { it.id == update.chatId }?.positions =
            chats.find { it.id == update.chatId }?.positions?.plus(
                update.position
            )
    }

    private fun handleChatLastMessage(update: UpdateChatLastMessage) = UpdateHandler { chats ->
        chats.find { it.id == update.chatId }?.apply {
            positions = update.positions
            lastMessage = update.lastMessage
        }
    }

    private fun handleFileUpdate(file: File) = UpdateHandler { chats ->
        chats.find { it.photo?.small?.id == file.id }?.photo?.small = file
    }

    fun interface UpdateHandler {
        operator fun invoke(chats: MutableList<Chat>)
    }
}
