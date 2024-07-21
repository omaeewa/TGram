package com.miracle.data.repository

import com.miracle.data.model.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authState: StateFlow<AuthState>
    val firstScreenLoaded: StateFlow<Boolean>

    suspend fun setAuthPhoneNumber(phone: String)
    suspend fun setAuthCode(code: String)
    suspend fun checkAuthPassword(password: String)
    fun setFirstScreenLoaded()
}