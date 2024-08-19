package com.miracle.data.repository

import com.miracle.data.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.DraftMessage
import org.drinkless.tdlib.TdApi.InputMessageContent
import org.drinkless.tdlib.TdApi.InputMessageReplyTo
import org.drinkless.tdlib.TdApi.MessageSendOptions
import org.drinkless.tdlib.TdApi.ReplyMarkup
import org.drinkless.tdlib.TdApi.UpdateChatDraftMessage


interface ChatRepository {

    suspend fun setChatDraftMessage(
        chatId: Long,
        messageThreadId: Long = 0,
        draftMessage: DraftMessage?
    )

    val messages: StateFlow<List<Message>>

    suspend fun loadMoreMessages(chatId: Long)
    suspend fun initializeMessages(chatId: Long)

    suspend fun sendMessage(
        chatId: Long,
        messageThreadId: Long = 0,
        replyTo: InputMessageReplyTo? = null,
        options: MessageSendOptions? = null,
        replyMarkup: ReplyMarkup? = null,
        inputMessageContent: InputMessageContent?
    )

}