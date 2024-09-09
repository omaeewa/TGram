package com.miracle.testing.repository

import com.miracle.data.model.Chat
import com.miracle.data.model.FormattedText
import com.miracle.data.model.Message
import com.miracle.data.model.MessageText
import com.miracle.data.model.toFormattedText
import com.miracle.data.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.InputMessageText

class ChatRepositoryTest(
    private val loadMessagesLimit: Int,
    private val chatsRepository: ChatsRepositoryTest
) : ChatRepository {

    override suspend fun setChatDraftMessage(
        chatId: Long,
        messageThreadId: Long,
        draftMessage: TdApi.DraftMessage?
    ) {
        chatsRepository.updateChat(chatId = chatId) {
            it.copy(draftMessage = draftMessage)
        }
    }

    private val _messages = MutableStateFlow(listOf<Message>())
    override val messages: StateFlow<List<Message>>
        get() = _messages

    override fun currentChatFlow(chatId: Long): Flow<Chat> = chatsRepository.chats
        .mapNotNull { it.find { it.id == chatId } }

    override suspend fun loadMoreMessages(chatId: Long) {
        val currentSize = _messages.value.size

        val newMessages = (currentSize until currentSize + loadMessagesLimit).map { id ->
            Message(
                id = id.toLong(),
                content = MessageText(text = FormattedText(text = "Message_${id}"))
            )
        }

        _messages.update { it + newMessages }
    }

    override suspend fun initializeMessages(chatId: Long) {
        val newMessages = (0 until 20).map { id ->
            Message(
                id = id.toLong(),
                content = MessageText(text = FormattedText(text = "Message${id}"))
            )
        }

        _messages.update { newMessages }
    }

    override suspend fun sendMessage(
        chatId: Long,
        messageThreadId: Long,
        replyTo: TdApi.InputMessageReplyTo?,
        options: TdApi.MessageSendOptions?,
        replyMarkup: TdApi.ReplyMarkup?,
        inputMessageContent: TdApi.InputMessageContent?
    ) {
        val newMessage = Message(
            id = _messages.value.size.toLong(),
            content = MessageText(text = (inputMessageContent as InputMessageText).text.toFormattedText())
        )

        chatsRepository.updateChat(chatId) {
            it.copy(draftMessage = null)
        }
        _messages.update { listOf(newMessage) + it }
    }

}