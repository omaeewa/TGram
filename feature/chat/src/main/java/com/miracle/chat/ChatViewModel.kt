package com.miracle.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.miracle.chat.model.ChatInfo
import com.miracle.chat.model.toChatInfo
import com.miracle.data.repository.AccountRepository
import com.miracle.data.repository.ChatRepository
import com.miracle.data.repository.ChatsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = ChatViewModel.Factory::class)
class ChatViewModel @AssistedInject constructor(
    @Assisted val chatId: Long,
    private val accountRepository: AccountRepository,
    private val chatsRepository: ChatsRepository,
    private val chatRepository: ChatRepository,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(chatId: Long): ChatViewModel
    }

    private val currentUserIdFlow = flow { emit(accountRepository.getMe().id) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, -1)

    val messagesPager = Pager(config = PagingConfig(40), pagingSourceFactory = {
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