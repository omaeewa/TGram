package com.miracle.data.repository

import com.miracle.common.Dispatcher
import com.miracle.common.TGramDispatchers.IO
import com.miracle.common.di.ApplicationScope
import com.miracle.data.model.Message
import com.miracle.data.model.toMessage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.getChatHistory
import kotlinx.telegram.coroutines.sendMessage
import kotlinx.telegram.coroutines.setChatDraftMessage
import kotlinx.telegram.flows.chatDraftMessageFlow
import kotlinx.telegram.flows.chatReadOutboxFlow
import kotlinx.telegram.flows.newMessageFlow
import org.drinkless.tdlib.TdApi
import javax.inject.Inject

class ChatRepositoryTdLib @Inject constructor(
    private val telegramApi: TelegramFlow,
    @Dispatcher(IO) private val dispatcherIo: CoroutineDispatcher,
    @ApplicationScope private val coroutineScope: CoroutineScope
) : ChatRepository {


    override val draftMessageUpdate = telegramApi.chatDraftMessageFlow()
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

    private suspend fun handleMessageUpdates(chatId: Long) {
        telegramApi.newMessageFlow().collect { newMessage ->
            if (newMessage.chatId == chatId && _messages.value.none { it.id == newMessage.id }) {
                _messages.update { listOf(newMessage.toMessage()) + it }
            }
        }
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

        _messages.update { listOf(sentMessage.toMessage()) + it  }
    }


}