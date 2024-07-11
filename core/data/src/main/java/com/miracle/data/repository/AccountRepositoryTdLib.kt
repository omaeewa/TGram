package com.miracle.data.repository

import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.getMe
import javax.inject.Inject

class AccountRepositoryTdLib @Inject constructor(
    private val telegramApi: TelegramFlow
) : AccountRepository {
    override suspend fun getMe() = telegramApi.getMe()
}