package com.miracle.data.repository

import com.miracle.model.ChatListItem
import com.miracle.model.Message
import kotlinx.coroutines.flow.StateFlow

interface ChatsRepository {

    val chats: StateFlow<List<ChatListItem>>
    suspend fun loadMore(limit: Int)
    suspend fun getMessages(
        chatId: Long,
        fromMessageId: Long,
        offset: Int,
        limit: Int,
        onlyLocal: Boolean
    ) : List<Message>
}