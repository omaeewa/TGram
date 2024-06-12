package com.miracle.chats.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.miracle.chats.ChatsRoute
import kotlinx.serialization.Serializable

@Serializable
object Chats

fun NavController.navigateToChats(navOptions: NavOptions? = null) = navigate(Chats, navOptions)

fun NavGraphBuilder.chatsScreen() = composable<Chats> {
    ChatsRoute()
}
