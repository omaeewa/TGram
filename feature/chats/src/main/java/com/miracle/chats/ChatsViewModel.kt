package com.miracle.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.chats.model.toChatListItem
import com.miracle.data.repository.ChatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatsRepository: ChatsRepository
) : ViewModel() {
    val chats = chatsRepository.chats
        .map {
            it.sortedByDescending { it.lastMessage?.date }
                .map { chat -> chat.toChatListItem() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            chatsRepository.loadMore(20)
        }
    }
}

