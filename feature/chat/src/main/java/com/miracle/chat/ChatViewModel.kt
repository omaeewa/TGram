package com.miracle.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.miracle.chat.model.ChatInfo
import com.miracle.chat.model.toChatInfo
import com.miracle.chat.navigation.Chat
import com.miracle.data.repository.AccountRepository
import com.miracle.data.repository.ChatRepository
import com.miracle.data.repository.ChatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val chatsRepository: ChatsRepository,
    private val chatRepository: ChatRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val chatSavedState = savedStateHandle.toRoute<Chat>()
    private val chatId get() = chatSavedState.chatId
    val currentUserIdFlow = flow { emit(accountRepository.getMe().id) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, -1)

    val messagesPager = Pager(config = PagingConfig(20), pagingSourceFactory = {
        ChatMessagesPagingSource(
            chatId = chatId,
            chatRepository = chatRepository
        )
    }).flow

    private val currentChatFlow = chatsRepository.chats.mapNotNull { it.find { it.id == chatId } }
    val chatInfo = combine(currentChatFlow, currentUserIdFlow) { currentChat, currentUserId ->
        currentChat.toChatInfo(currentUserId)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ChatInfo.empty)

}