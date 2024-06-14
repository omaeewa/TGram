package com.miracle.chats.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.miracle.chats.ChatsRoute
import kotlinx.serialization.Serializable

@Serializable
object Chats

fun NavController.navigateToChats(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) = navigate(Chats, navOptionsBuilder)

fun NavGraphBuilder.chatsScreen() = composable<Chats> {
    ChatsRoute()
}
