package com.miracle.data.repository

import com.miracle.model.ChatListItem
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {

    val chats: Flow<List<ChatListItem>>
    suspend fun loadMore(limit: Int)
}