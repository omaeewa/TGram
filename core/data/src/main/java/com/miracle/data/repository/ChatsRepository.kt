package com.miracle.data.repository

import kotlinx.coroutines.flow.StateFlow
import org.drinkless.tdlib.TdApi.Chat

interface ChatsRepository {

    val chats: StateFlow<List<Chat>>
    suspend fun loadMore(limit: Int)

}