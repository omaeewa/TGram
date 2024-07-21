package com.miracle.tgram.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.miracle.authorization.AuthorizationRoute
import com.miracle.chat.ChatRoute
import com.miracle.chat.ChatViewModel
import com.miracle.chat.navigation.Chat
import com.miracle.chats.ChatsRoute
import com.miracle.chats.ChatsViewModel
import com.miracle.ui.theme.mColors

class PlaceholderScreen : Screen {
    @Composable
    override fun Content() {
        Box(Modifier.background(mColors.surface).fillMaxSize())
    }
}

class AuthorizationScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AuthorizationRoute(navigateToChats = { navigator.replace(ChatsScreen()) })
    }
}

data class ChatScreen(val chatId: Long) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<ChatViewModel, ChatViewModel.Factory> { factory ->
            factory.create(chatId)
        }

        ChatRoute(
            chatData = Chat(chatId = chatId),
            onBackBtnClick = navigator::pop,
            viewModel = viewModel
        )
    }
}

class ChatsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<ChatsViewModel>()

        ChatsRoute(
            navigateToChat = { chatId ->
                val chatData = Chat(chatId)
                navigator.push(ChatScreen(chatId))
            },
            viewModel = viewModel
        )
    }
}