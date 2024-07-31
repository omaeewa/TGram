package com.miracle.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.chat.model.ChatInfo
import com.miracle.chat.model.toChatInfo
import com.miracle.chat.model.toMessage
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.DraftMessage
import org.drinkless.tdlib.TdApi.InputMessageText

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

    init {
        viewModelScope.launch {
            chatRepository.initializeMessages(chatId)
        }
    }

    private val currentUserIdFlow = accountRepository.me.map { it.id }
        .stateIn(viewModelScope, SharingStarted.Eagerly, -1)

    val messages = chatRepository.messages.map { it.map { it.toMessage() } }.stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

    private val currentChatFlow = chatsRepository.chats
        .mapNotNull { it.find { it.id == chatId } }


    val chatInfo = combine(currentChatFlow, currentUserIdFlow) { currentChat, currentUserId ->
        currentChat.toChatInfo(currentUserId)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ChatInfo.empty)


    val draftMessage = currentChatFlow.map { it.draftMessage }
        .map {
            when (val input = it?.inputMessageText) {
                is InputMessageText -> input.text.text
                else -> ""
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, ""
        )


    fun onDraftMessageChange(value: String) {
        val formattedText = TdApi.FormattedText(
            value,
            emptyArray()
        )
        val inputMessageText = InputMessageText(
            formattedText, null, false
        )
        val draftMessage = if (value.isEmpty()) null
        else DraftMessage(
            null,
            System.currentTimeMillis().toInt(),
            inputMessageText,
            0
        )

        viewModelScope.launch {
            chatRepository.setChatDraftMessage(
                chatId = chatId,
                draftMessage = draftMessage
            )
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            chatRepository.sendMessage(
                chatId = chatId,
                inputMessageContent = InputMessageText(
                    TdApi.FormattedText(
                        draftMessage.value,
                        emptyArray()
                    ),
                    null,
                    true
                )
            )
        }
    }

    fun loadMoreMessages() {
        viewModelScope.launch {
            chatRepository.loadMoreMessages(chatId)
        }
    }

}