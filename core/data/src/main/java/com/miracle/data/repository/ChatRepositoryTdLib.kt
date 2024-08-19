package com.miracle.data.repository

import com.miracle.common.Dispatcher
import com.miracle.common.TGramDispatchers.IO
import com.miracle.common.di.ApplicationScope
import com.miracle.data.model.Message
import com.miracle.data.model.MessagePhoto
import com.miracle.data.model.toMessage
import com.miracle.data.updateshandler.MessagesUpdateHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.getChatHistory
import kotlinx.telegram.coroutines.sendMessage
import kotlinx.telegram.coroutines.setChatDraftMessage
import org.drinkless.tdlib.TdApi
import javax.inject.Inject

class ChatRepositoryTdLib @Inject constructor(
    private val telegramApi: TelegramFlow,
    private val messagesUpdateHandler: MessagesUpdateHandler
) : ChatRepository {


    override suspend fun setChatDraftMessage(
        chatId: Long,
        messageThreadId: Long,
        draftMessage: TdApi.DraftMessage?
    ) {
        telegramApi.setChatDraftMessage(
            chatId = chatId,
            messageThreadId = messageThreadId,
            draftMessage = draftMessage
        )
    }

    private val _messages = MutableStateFlow(emptyList<Message>())
    override val messages: StateFlow<List<Message>> = _messages

    override suspend fun loadMoreMessages(chatId: Long) {
        val messages = telegramApi.getChatHistory(
            chatId = chatId,
            fromMessageId = _messages.value.lastOrNull()?.id ?: 0,
            offset = 0,
            limit = 40,
            onlyLocal = false
        ).messages.map { it.toMessage() }

        _messages.update { it + messages }

    }

    override suspend fun initializeMessages(chatId: Long) {
        loadMoreMessages(chatId) // load first message
        loadMoreMessages(chatId) // load other messages
        handleMessageUpdates(chatId)
    }

    private suspend fun handleMessageUpdates(chatId: Long) = coroutineScope {
        launch {
            messagesUpdateHandler.handleMessageUpdates(chatId).collect { handler ->
                _messages.update { handler(it.toMutableList()) }
            }
        }
        launch { downloadImages() }
    }


    override suspend fun sendMessage(
        chatId: Long,
        messageThreadId: Long,
        replyTo: TdApi.InputMessageReplyTo?,
        options: TdApi.MessageSendOptions?,
        replyMarkup: TdApi.ReplyMarkup?,
        inputMessageContent: TdApi.InputMessageContent?
    ) {
        val sentMessage = telegramApi.sendMessage(
            chatId = chatId,
            messageThreadId = messageThreadId,
            replyTo = replyTo,
            options = options,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
        )

        _messages.update { sentMessage.toMessage() withList  it }
    }

    private infix fun Message.withList(messages: List<Message>) =
        if (messages.any { it.id == id }) {
            messages.map { if (it.id == id) this else it }
        } else {
            listOf(this) + messages
        }


    private suspend fun downloadImages() {
        messages.collect { messageList ->
            messageList.map { it.content }.forEach { content ->
                if (content is MessagePhoto) {
                    val file = content.photo.sizes.lastOrNull()?.photo
                    if (file != null) {
                        telegramApi.downloadFile(file)
                    }
                }
            }
        }
    }
}