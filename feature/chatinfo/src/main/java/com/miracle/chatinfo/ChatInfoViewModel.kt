package com.miracle.chatinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.chatinfo.model.ChatInfo
import com.miracle.chatinfo.model.toChatInfo
import com.miracle.data.repository.ChatsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn


@HiltViewModel(assistedFactory = ChatInfoViewModel.Factory::class)
class ChatInfoViewModel @AssistedInject constructor(
    @Assisted val chatId: Long,
    chatsRepository: ChatsRepository,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(chatId: Long): ChatInfoViewModel
    }

    private val currentChatFlow = chatsRepository.chats
        .mapNotNull { it.find { it.id == chatId } }


    val chatInfo = currentChatFlow.map { it.toChatInfo() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, ChatInfo.empty)

}
