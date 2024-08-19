package com.miracle.data.repository

import com.miracle.common.Dispatcher
import com.miracle.common.TGramDispatchers.IO
import com.miracle.common.di.ApplicationScope
import com.miracle.data.updateshandler.ChatsUpdateHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.downloadFile
import kotlinx.telegram.coroutines.loadChats
import org.drinkless.tdlib.TdApi.File
import javax.inject.Inject

class ChatsRepositoryTdLib @Inject constructor(
    private val telegramApi: TelegramFlow,
    @Dispatcher(IO) private val dispatcherIo: CoroutineDispatcher,
    @ApplicationScope private val coroutineScope: CoroutineScope,
    private val chatsUpdateHandler: ChatsUpdateHandler
) : ChatsRepository {

    override val chats = chatsUpdateHandler.chatsWithUpdates
        .stateIn(
            coroutineScope,
            SharingStarted.Eagerly,
            emptyList()
        )


    override suspend fun loadMore(limit: Int) = try {
        telegramApi.loadChats(null, limit)
    } catch (e: Throwable) {
        //All chats were loaded
    }


}

suspend fun TelegramFlow.downloadFile(file: File?) {
    file ?: return

    if (file.local.canBeDownloaded && !file.local.isDownloadingActive && !file.local.isDownloadingCompleted)
        this.downloadFile(file.id, 1, 0, 0, true)
}

