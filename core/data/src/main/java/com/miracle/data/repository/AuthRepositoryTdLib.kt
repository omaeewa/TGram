package com.miracle.data.repository

import android.content.Context
import android.os.Build
import com.miracle.common.di.ApplicationScope
import com.miracle.data.BuildConfig
import com.miracle.data.model.AuthState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.checkAuthenticationCode
import kotlinx.telegram.coroutines.checkAuthenticationPassword
import kotlinx.telegram.coroutines.setAuthenticationPhoneNumber
import kotlinx.telegram.flows.authorizationStateFlow
import org.drinkless.tdlib.TdApi
import java.util.Locale
import javax.inject.Inject

class AuthRepositoryTdLib @Inject constructor(
    private val telegramApi: TelegramFlow,
    @ApplicationContext private val appContext: Context,
    @ApplicationScope private val coroutineScope: CoroutineScope
) : AuthRepository {

    override val authState = telegramApi.authorizationStateFlow()
        .onEach(::checkRequiredParams)
        .map {
            when (it) {
                is TdApi.AuthorizationStateReady -> AuthState.Ready
                is TdApi.AuthorizationStateWaitCode -> AuthState.WaitCode
                is TdApi.AuthorizationStateWaitPassword -> AuthState.WaitPassword
                is TdApi.AuthorizationStateWaitPhoneNumber -> AuthState.WaitPhoneNumber
                else -> AuthState.Unexpected
            }
        }.stateIn(
            coroutineScope, SharingStarted.Eagerly, AuthState.Unexpected
        )

    private val _firstScreenLoaded = MutableStateFlow(false)
    override val firstScreenLoaded: StateFlow<Boolean> = _firstScreenLoaded

    override suspend fun setAuthPhoneNumber(phone: String) {
        telegramApi.setAuthenticationPhoneNumber(phone, null)
    }

    override suspend fun setAuthCode(code: String) {
        telegramApi.checkAuthenticationCode(code)
    }

    override suspend fun checkAuthPassword(password: String) {
        telegramApi.checkAuthenticationPassword(password)
    }

    override fun setFirstScreenLoaded() {
        if (!_firstScreenLoaded.value)
            _firstScreenLoaded.update { true }
    }

    private suspend fun checkRequiredParams(state: TdApi.AuthorizationState?) {
        when (state) {
            is TdApi.AuthorizationStateWaitTdlibParameters ->
                telegramApi.sendFunctionLaunch(tgCredentials)
        }
    }


    //Replace with your personal telegram credentials
    // Go to https://my.telegram.org to obtain api id (integer) and api hash (string).
    private val tgCredentials = TdApi.SetTdlibParameters().apply {
        useTestDc = false
        databaseDirectory = appContext.filesDir.absolutePath
        apiId = BuildConfig.apiId.toInt()
        apiHash = BuildConfig.apiHash
        useMessageDatabase = true
        useSecretChats = true
        systemLanguageCode = Locale.getDefault().language
        deviceModel = Build.MODEL
        applicationVersion = "1.0"
    }
}
