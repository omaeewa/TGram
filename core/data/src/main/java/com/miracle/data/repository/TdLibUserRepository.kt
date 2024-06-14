package com.miracle.data.repository

import com.miracle.data.model.toUserData
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.getMe
import kotlinx.telegram.flows.userFlow
import javax.inject.Inject

class TdLibUserRepository @Inject constructor(
    private val telegramApi: TelegramFlow,
) : UserRepository {

    override val userData = flow {
        val me = telegramApi.getMe().toUserData()
        emit(me)
        emitAll(telegramApi.userFlow().filter { it.id == me.id }.map { it.toUserData() })
    }
}