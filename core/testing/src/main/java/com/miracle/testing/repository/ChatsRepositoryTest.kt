package com.miracle.testing.repository

import com.miracle.data.model.Chat
import com.miracle.data.repository.ChatsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ChatsRepositoryTest: ChatsRepository {

    private val _chats = MutableStateFlow(emptyList<Chat>())
    override val chats: StateFlow<List<Chat>>
        get() = _chats

    override suspend fun loadMore(limit: Int) {
        val currentSize = _chats.value.size

        val newChats = (currentSize until currentSize + limit).map { id ->
            Chat(id = id.toLong(), title = "Chat${id}")
        }

        _chats.update { it + newChats }
    }

    fun updateChat(chatId: Long, block: (Chat) -> Chat) {
        _chats.update {
            it.map {
                if (it.id == chatId) block(it) else it
            }
        }
    }
}