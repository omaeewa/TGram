package com.miracle.data.repository

import android.content.Context
import android.os.Build
import com.miracle.data.BuildConfig
import com.miracle.data.model.AuthState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.checkAuthenticationCode
import kotlinx.telegram.coroutines.checkAuthenticationPassword
import kotlinx.telegram.coroutines.checkDatabaseEncryptionKey
import kotlinx.telegram.coroutines.setAuthenticationPhoneNumber
import kotlinx.telegram.coroutines.setTdlibParameters
import kotlinx.telegram.flows.authorizationStateFlow
import org.drinkless.td.libcore.telegram.TdApi
import java.util.Locale
import javax.inject.Inject

class TdLibAuthAuthRepository @Inject constructor(
    private val telegramApi: TelegramFlow,
    @ApplicationContext private val appContext: Context
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
        }

    override suspend fun setAuthPhoneNumber(phone: String) {
        telegramApi.setAuthenticationPhoneNumber(phone, null)
    }

    override suspend fun setAuthCode(code: String) {
        telegramApi.checkAuthenticationCode(code)
    }

    override suspend fun checkAuthPassword(password: String) {
        telegramApi.checkAuthenticationPassword(password)
    }

    override suspend fun checkDatabaseEncryptionKey() {
        telegramApi.checkDatabaseEncryptionKey()
    }


    private suspend fun checkRequiredParams(state: TdApi.AuthorizationState?) {
        when (state) {
            is TdApi.AuthorizationStateWaitTdlibParameters ->
                telegramApi.setTdlibParameters(tgCredentials)

            is TdApi.AuthorizationStateWaitEncryptionKey ->
                telegramApi.checkDatabaseEncryptionKey()
        }
    }


    //Replace with your personal telegram credentials
    // Go to https://my.telegram.org to obtain api id (integer) and api hash (string).
    private val tgCredentials = TdApi.TdlibParameters().apply {
        apiId = BuildConfig.apiId.toInt()
        apiHash = BuildConfig.apiHash
        useMessageDatabase = true
        useSecretChats = true
        systemLanguageCode = Locale.getDefault().language
        databaseDirectory = appContext.filesDir.absolutePath
        deviceModel = Build.MODEL
        systemVersion = Build.VERSION.RELEASE
        applicationVersion = "1.0"
        enableStorageOptimizer = true
    }
}
