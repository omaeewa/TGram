package com.miracle.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.data.model.UserData
import com.miracle.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    val userData = userRepository.userData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = UserData()
    )

}