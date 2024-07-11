package com.miracle.data.repository

import org.drinkless.tdlib.TdApi.User

interface AccountRepository {
    suspend fun getMe(): User
}