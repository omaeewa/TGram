package com.miracle.data.repository

import com.miracle.common.Dispatcher
import com.miracle.common.TGramDispatchers.IO
import com.miracle.common.di.ApplicationScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.getChatHistory
import javax.inject.Inject

class ChatRepositoryTdLib @Inject constructor(
    private val telegramApi: TelegramFlow,
    @Dispatcher(IO) private val dispatcherIo: CoroutineDispatcher,
    @ApplicationScope private val coroutineScope: CoroutineScope
) : ChatRepository {

    override suspend fun getMessages(
        chatId: Long,
        fromMessageId: Long,
        offset: Int,
        limit: Int,
        onlyLocal: Boolean
    ) = telegramApi.getChatHistory(
        chatId, fromMessageId, offset, limit, onlyLocal
    ).messages.toList()
}