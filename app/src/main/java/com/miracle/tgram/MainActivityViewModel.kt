package com.miracle.tgram

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.data.model.AuthState
import com.miracle.data.repository.AuthRepository
import com.miracle.tgram.MainActivityUiState.Loading
import com.miracle.tgram.MainActivityUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {

    val uiState = authRepository.authState.map {
        when (it) {
            AuthState.Ready -> Success(true)
            AuthState.WaitPhoneNumber, AuthState.WaitCode -> Success(false)
            else -> Loading
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.Eagerly,
    )

    val isFirstScreenLoaded = authRepository.firstScreenLoaded
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val isAuthorized: Boolean) : MainActivityUiState
}