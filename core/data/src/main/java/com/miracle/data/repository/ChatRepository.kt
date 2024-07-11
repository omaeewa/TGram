package com.miracle.data.repository

import org.drinkless.tdlib.TdApi


interface ChatRepository {
    suspend fun getMessages(
        chatId: Long,
        fromMessageId: Long,
        offset: Int,
        limit: Int,
        onlyLocal: Boolean
    ) : List<TdApi.Message>
}