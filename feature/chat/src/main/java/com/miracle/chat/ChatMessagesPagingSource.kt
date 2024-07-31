package com.miracle.chat

//class ChatMessagesPagingSource(
//    private val chatId: Long,
//    private val chatRepository: ChatRepository
//) : PagingSource<Long, Message>() {
//    override fun getRefreshKey(state: PagingState<Long, Message>): Long? {
//        return null
//    }
//
//    override suspend fun load(params: LoadParams<Long>) = try {
//        val messages = chatRepository.loadMessages(
//            chatId = chatId,
//            fromMessageId = params.key ?: 0,
//            offset = 0,
//            limit = params.loadSize,
//            onlyLocal = false
//        )
//
////        delay(100)
////        val messages = ((params.key ?: 0)..((params.key ?: 0) + 100)).map {
////            Message.dummy.copy(id = it)
////        }
//
//        LoadResult.Page(
//            data = messages,
//            prevKey = null,
//            nextKey = messages.last().id
//        )
//    } catch (t: Throwable) {
//        LoadResult.Error(t)
//    }
//}