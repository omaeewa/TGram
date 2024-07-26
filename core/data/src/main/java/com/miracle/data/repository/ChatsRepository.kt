package com.miracle.data.repository

import com.miracle.data.model.Chat
import kotlinx.coroutines.flow.StateFlow

interface ChatsRepository {

    val chats: StateFlow<List<Chat>>
    suspend fun loadMore(limit: Int)

}