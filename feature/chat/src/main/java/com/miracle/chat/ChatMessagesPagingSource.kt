package com.miracle.chat

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miracle.data.repository.ChatsRepository
import com.miracle.model.Message

class ChatMessagesPagingSource(
    private val chatId: Long,
    private val chatsRepository: ChatsRepository
) : PagingSource<Long, Message>() {
    override fun getRefreshKey(state: PagingState<Long, Message>): Long? {
        return null
    }

    override suspend fun load(params: LoadParams<Long>) = try {
        val messages = chatsRepository.getMessages(
            chatId = chatId,
            fromMessageId = params.key ?: 0,
            offset = 0,
            limit = params.loadSize,
            onlyLocal = false
        )

        LoadResult.Page(
            data = messages,
            prevKey = null,
            nextKey = messages.last().id
        )
    } catch (t: Throwable) {
        LoadResult.Error(t)
    }
}