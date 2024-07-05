package com.miracle.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.miracle.chat.navigation.Chat
import com.miracle.data.repository.ChatsRepository
import com.miracle.model.ChatListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatsRepository: ChatsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val chat = savedStateHandle.toRoute<Chat>()

    val messagesPager = Pager(config = PagingConfig(20), pagingSourceFactory = {
        ChatMessagesPagingSource(
            chatId = chat.chatId,
            chatsRepository = chatsRepository
        )
    }).flow

    val chatInfo = chatsRepository.chats.mapNotNull { it.find { it.id == chat.chatId } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, ChatListItem.empty)

}