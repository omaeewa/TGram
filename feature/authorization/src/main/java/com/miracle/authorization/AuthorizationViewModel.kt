package com.miracle.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.common.Dispatcher
import com.miracle.common.TGramDispatchers.IO
import com.miracle.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(IO) private val dispatcherIo: CoroutineDispatcher
) : ViewModel() {

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _authPassword = MutableStateFlow("")
    val authPassword : StateFlow<String> = _authPassword

    private val _authCode = MutableStateFlow("")
    val authCode: StateFlow<String> = _authCode

    val authState = authRepository.authState

    fun onPhoneNumberChange(value: String) = _phoneNumber.update { value }

    fun setAuthPhoneNumber() = viewModelScope.launch(dispatcherIo) {
        authRepository.setAuthPhoneNumber(phoneNumber.value)
    }

    fun onAuthCodeChange(value: String) = _authCode.update { value }

    fun onPasswordChange(value: String) = _authPassword.update { value }

    fun setAuthCode() = viewModelScope.launch(dispatcherIo) {
        authRepository.setAuthCode(authCode.value)
    }

    fun checkAuthPassword() = viewModelScope.launch(){
        authRepository.checkAuthPassword(authPassword.value)
    }
    fun updateFirstScreenLoaded() {
        authRepository.setFirstScreenLoaded()
    }
}