package com.miracle.data.repository

import com.miracle.data.model.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val authState: Flow<AuthState>


    suspend fun setAuthPhoneNumber(phone: String)
    suspend fun setAuthCode(code: String)
    suspend fun checkAuthPassword(password: String)
    suspend fun checkDatabaseEncryptionKey()
}