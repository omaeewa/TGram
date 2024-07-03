package com.miracle.data.repository

import com.miracle.model.ChatListItem
import kotlinx.coroutines.flow.StateFlow

interface ChatsRepository {

    val chats: StateFlow<List<ChatListItem>>
    suspend fun loadMore(limit: Int)
}