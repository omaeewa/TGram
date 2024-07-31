package com.miracle.data.repository

import com.miracle.common.di.ApplicationScope
import com.miracle.data.model.User
import com.miracle.data.model.toUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.getMe
import kotlinx.telegram.flows.fileFlow
import kotlinx.telegram.flows.userFlow
import org.drinkless.tdlib.TdApi
import javax.inject.Inject

class AccountRepositoryTdLib @Inject constructor(
    private val telegramApi: TelegramFlow,
    @ApplicationScope private val coroutineScope: CoroutineScope
) : AccountRepository {

    override val me = flow {
        emitAll(
            merge(
                telegramApi.userFlow().map(::handleUserUpdate),
                telegramApi.fileFlow().map(::handleFileUpdate)
            )
                .scan(telegramApi.getMe().toUser()) { currentUser, update ->
                    update(currentUser)
                }
        )
    }
        .onEach {
            ensureProfilePhotoIsDownloaded(it)
        }

    private fun handleUserUpdate(userUpdate: TdApi.User) = UpdateHandler { user ->
        if (user.id == userUpdate.id) userUpdate.toUser() else user
    }

    private fun handleFileUpdate(file: TdApi.File) = UpdateHandler { user ->
        if (user.profilePhoto?.small?.id == file.id) {
            user.copy(profilePhoto = user.profilePhoto.copy(small = file))
        } else {
            user
        }
    }


    private fun ensureProfilePhotoIsDownloaded(user: User) {
        coroutineScope.launch {
            telegramApi.downloadFile(user.profilePhoto?.small)
        }
    }

    fun interface UpdateHandler {
        operator fun invoke(chats: User): User
    }
}