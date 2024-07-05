package com.miracle.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.miracle.chat.ChatRoute
import kotlinx.serialization.Serializable

@Serializable
data class Chat(val chatId: Long)

fun NavController.navigateToChat(
    chatId: Long,
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}
) = navigate(Chat(chatId), navOptionsBuilder)

fun NavGraphBuilder.chatScreen(onBackBtnClick: () -> Unit) = composable<Chat> {
    ChatRoute(chatData = it.toRoute(), onBackBtnClick = onBackBtnClick)
}
